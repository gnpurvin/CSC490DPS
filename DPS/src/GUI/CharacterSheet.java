package GUI;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author Spencer
 */
@XmlRootElement
public class CharacterSheet {
    String Name;
    String ClassName;
    String Race;
    String Level;
    String Str;
    String Dex;
    String Int;
    String Con;
    String Wis;
    String Cha;
    String HP;
    String AC;
    /**
     * @return the Name
     */
    public String getName() {
        return Name;
    }

    /**
     * @param Name the Name to set
     */
    @XmlElement
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * @return the Class
     */
    public String getClassName() {
        return ClassName;
    }

    /**
     * @param Class the Class to set
     */
    @XmlElement
    public void setClassName(String ClassName) {
        this.ClassName = ClassName;
    }

    /**
     * @return the Race
     */
    public String getRace() {
        return Race;
    }

    /**
     * @param Race the Race to set
     */
    @XmlElement
    public void setRace(String Race) {
        this.Race = Race;
    }

    /**
     * @return the Level
     */
    public String getLevel() {
        return Level;
    }

    /**
     * @param Level the Level to set
     */
    @XmlElement
    public void setLevel(String Level) {
        this.Level = Level;
    }

    /**
     * @return the Str
     */
    public String getStr() {
        return Str;
    }

    /**
     * @param Str the Str to set
     */
    @XmlElement
    public void setStr(String Str) {
        this.Str = Str;
    }

    /**
     * @return the Dex
     */
    public String getDex() {
        return Dex;
    }

    /**
     * @param Dex the Dex to set
     */
    @XmlElement
    public void setDex(String Dex) {
        this.Dex = Dex;
    }

    /**
     * @return the Int
     */
    public String getInt() {
        return Int;
    }

    /**
     * @param Int the Int to set
     */
    @XmlElement
    public void setInt(String Int) {
        this.Int = Int;
    }

    /**
     * @return the Con
     */
    public String getCon() {
        return Con;
    }

    /**
     * @param Con the Con to set
     */
    @XmlElement
    public void setCon(String Con) {
        this.Con = Con;
    }

    /**
     * @return the Wis
     */
    public String getWis() {
        return Wis;
    }

    /**
     * @param Wis the Wis to set
     */
    @XmlElement
    public void setWis(String Wis) {
        this.Wis = Wis;
    }

    /**
     * @return the Cha
     */
    public String getCha() {
        return Cha;
    }

    /**
     * @param Cha the Cha to set
     */
    @XmlElement
    public void setCha(String Cha) {
        this.Cha = Cha;
    }

    /**
     * @return the HP
     */
    public String getHP() {
        return HP;
    }

    /**
     * @param HP the HP to set
     */
    @XmlElement
    public void setHP(String HP) {
        this.HP = HP;
    }

    /**
     * @return the AC
     */
    public String getAC() {
        return AC;
    }

    /**
     * @param AC the AC to set
     */
    @XmlElement
    public void setAC(String AC) {
        this.AC = AC;
    }

    public CharacterSheet(){
    }
}
