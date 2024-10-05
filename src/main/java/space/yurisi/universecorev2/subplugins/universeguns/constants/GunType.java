package space.yurisi.universecorev2.subplugins.universeguns.constants;

public enum GunType {

    AR(1),
    SR(10),
    SMG(20),
    SG(30),
    HG(40),
    LMG(50),
    EX(60);
    
    
    private int id;

    GunType(int id){
        this.id = id;
    }
}
