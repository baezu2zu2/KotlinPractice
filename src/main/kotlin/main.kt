import kotlin.random.Random

fun main() {

}

//Person : Knight, Ninja, Hunter, Magician
//Knight : Heavy Knight, Knight, Spear Knight
//Ninja : Fast Ninja, Silent Ninja
//Hunter : Bow Hunter, Gun Hunter
//Magician : Healer, Magical Hunter, Magical Warrior
/*
 Person

 Hp
 Atk
 Intl
 Spd
 Luk
 Weapon
 Armor
 reach
 */

/*
 Knight

 Hp
 Atk
 #Def
 Intl
 Spd
 Luk
 Armor
 reach
 */

interface Weapon{
    var atk : Double
    var lvl : Int
    var reach : Double
}

interface Armor{
    var dfc : Double
    var lvl : Double
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
    var reach : Double

    fun attack(defencer : Person){
        var atkPwr = atk
        var damagerDfc : Double = 0.0

        if (weapon != null){
            if (defencer.armor != null) {
                atkPwr = weapon!!.atk
                for(i in defencer.armor!!){
                    damagerDfc += i.dfc
                }
                atkPwr -= damagerDfc
            }else{
                atkPwr = weapon!!.atk

            }
        }else {
            if (defencer.armor != null) {
                for(i in defencer.armor!!){
                    damagerDfc += i.dfc
                }
                atkPwr -= damagerDfc
            }
        }
        if (defencer is Knight){
            if (defencer.defence(this, atkPwr)){
                return
            }
        }
        if (atkPwr > 0) {
            defencer.hp -= atkPwr
        }else{
            defencer.hp--
        }
    }
}

interface Knight : Person{
    var dfc : Double

    fun defence(damager: Person, damage : Double) : Boolean{
        if (Random.nextInt(1, 100) < luk){
            damager.hp -= damage
            return true;
        }
        return false;
    }
}

class HeavyKnight : Knight{
    override var hp = 100.0
    override var atk = 5.0
    override var intl = 10.0
    override var spd = 3.0
    override var luk = 5
    override var lvl = 1
    override var weapon: Weapon? = null
    override var armor: Array<Armor>? = null
    override var reach = 1.0
    override var dfc = 3.0
}