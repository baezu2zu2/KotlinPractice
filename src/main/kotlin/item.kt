interface Item{
    var name : String
}

val weapons = arrayListOf<Weapon>()

interface Weapon : Item{
    var atk : Double
    val lvlUpNum : Double
    val weaponType : WeaponType
    var lvl : Int

    fun levelUp(){
        lvl++
        atk += lvl*lvlUpNum
    }
}

class PsychicWeapon(override val weaponType : WeaponType, override var lvl : Int = 1) : Weapon{
    override var atk : Double = weaponType.atk
    override var name : String = weaponType.typeName
    override val lvlUpNum : Double = weaponType.lvlUpNum

    override fun levelUp(){
        lvl++
        atk += lvl*lvlUpNum*1.25
    }
}

class MagicWeapon(override val weaponType : WeaponType, override var lvl : Int = 1) : Weapon {
    init{

    }

    override var atk : Double = weaponType.atk
    override var name : String = weaponType.typeName
    override val lvlUpNum : Double = weaponType.lvlUpNum
    var mana = weaponType.mana

    override fun levelUp(){
        lvl++
        atk += lvl*lvlUpNum*1.35
        mana += lvl*lvlUpNum*1.1
    }
}

enum class WeaponType(open var atk : Double, open val typeName : String, open val lvlUpNum : Double, val mana : Double = 0.0){
    BAESUHAN_SWORD(1.0, "배수한의 검", 0.75),
    HAOJUNWAN_ACNE(5.0, "허준원의 여드름",2.1),
    MOTHER_HAND(7.5, "어머니의 손맛", 2.0),
    FOOT_SMELL(4.5, "발냄새", 1.7, 1.0);
}