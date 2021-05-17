import kotlin.math.round
import kotlin.random.Random

fun main() {
    val bazu = HeavyKnight("바쭈", true)
    val papa = HeavyKnight("파파", true)
    val players = arrayListOf<Person>(bazu, papa)

    bazu.weapon = PsychicWeapon(WeaponType.MOTHER_HAND)
    papa.weapon = PsychicWeapon(WeaponType.BAESUHAN_SWORD)

    while (bazu.hp > 0 && papa.hp > 0){
        printPersonInfo(bazu)
        println()
        printPersonInfo(papa)
        print("턴을 넘기려면 엔터>>")
        readLine()
        val papaAttacked = bazu.attack(papa)
        val bazuAttacked = papa.attack(bazu)

        println("\n")

        println("바쭈는 $papaAttacked 데미지를 파파에게 가했습니다!")
        println("파파는 $bazuAttacked 데미지를 바쭈에게 가했습니다!")
        for (i in players){
            i.roundVars()
        }
    }
    for (i in players){
        if (i.hp > 0){
            println("${i.name}님 께서 승리하셨습니다!")
        }
    }
}

//Person : Knight, Ninja, Hunter, Magician
//Knight : Heavy Knight, Knight, Spear Knight
//Ninja : Fast Ninja, Silent Ninja
//Hunter : Bow Hunter, Gun Hunter
//Magician : Healer, Magical Hunter, Magical Warrior

interface Weapon{
    var atk : Double
    var lvl : Int
    var reach : Double
    var name : String
}

interface Armor{
    var dfc : Double
    var lvl : Double
    var name : String
}

interface Person{
    var hp : Double
    var atk : Double
    var intl : Double
    var spd : Double
    var luk : Int
    var lvl : Int
    var weapon : Weapon?
    var armor : Array<Armor>?
    var name : String
    val roleName : String

    fun attack(defencer : Person) : Double{
        var atkPwr = atk
        var damagerDfc = 0.0
        if (defencer is Knight){
            damagerDfc += defencer.dfc
        }

        if (weapon != null){
            if (defencer.armor != null) {
                atkPwr = weapon!!.atk
                for(i in defencer.armor!!){
                    damagerDfc += i.dfc
                }
            }else{
                atkPwr += weapon!!.atk
            }
        }else {
            if (defencer.armor != null) {
                for(i in defencer.armor!!){
                    damagerDfc += i.dfc
                }
            }
        }

        atkPwr -= damagerDfc

        atkPwr = round(atkPwr*10)/10

        if (defencer is Knight && atkPwr > 0){
            if (defencer.defence(this, atkPwr)){
                return -atkPwr
            }
        }else if (avoid(this)){
            return 0.0
        }

        if (atkPwr > 0) {
            defencer.hp -= atkPwr
        }else{
            defencer.hp--
            return -1.0
        }

        return atkPwr
    }

    fun avoid(damager: Person) : Boolean{
        return damager.spd < spd && Random.nextInt(1, 100) < luk*spd-damager.spd
    }

    fun randSet(){
        hp = Random.nextDouble(hp / 2, hp * 2)
        atk = Random.nextDouble(atk / 2, atk * 2)
        intl = Random.nextDouble(intl / 2, intl * 2)
        spd = Random.nextDouble(spd / 2, spd * 2)
        luk = Random.nextInt(luk / 2, luk * 2)
    }

    fun roundVars(){
        hp = round(hp*10)/10
        atk = round(atk*10)/10
        intl = round(intl*10)/10
        spd = round(spd*10)/10
    }
}

interface Knight : Person{
    var dfc : Double

    fun defence(damager: Person, damage : Double) : Boolean{
        if (Random.nextInt(1, 100) < luk){
            damager.hp -= damage
            return true
        }
        return false
    }

    override fun randSet(){
        super.randSet()
        dfc = Random.nextDouble(dfc / 2, dfc * 2)
    }

    override fun roundVars(){
        super.roundVars()
        dfc = round(dfc*10)/10
    }
}

class HeavyKnight(override var name : String, rand : Boolean=false) : Knight{
    override val roleName = "헤비 나이트"
    override var hp = 100.0
    override var atk = 10.0
    override var intl = 10.0
    override var spd = 3.0
    override var luk = 5
    override var lvl = 1
    override var weapon: Weapon? = null
    override var armor: Array<Armor>? = null
    override var dfc = 3.0

    init {
        if (rand) {
           randSet()
        }
        roundVars()
    }
}

fun printPersonInfo(person :Person){
    println("이름 : ${person.name}")
    println("레벨 : ${person.lvl}")
    println("직업 : ${person.roleName}")
    println("능지 : ${person.intl}")
    println("행운 : ${person.luk}")
    println("속도 : ${person.spd}")
    println("체력 : ${person.hp}")

    var atk = person.atk
    if (person.weapon != null){
        atk += person.weapon!!.atk
        println("무기 : ${person.weapon!!.name}")
    }
    println("공격력 : $atk")

    var dfc = 0.0
    if (person.armor != null){
        for (i in person.armor!!.indices){
            dfc += person.armor!![i].dfc
            println("$armorPosType[i] : ${person.armor!![i]}")
        }
    }

    if (person is Knight){
        dfc += person.dfc
    }
    println("방어력 : $dfc")
}