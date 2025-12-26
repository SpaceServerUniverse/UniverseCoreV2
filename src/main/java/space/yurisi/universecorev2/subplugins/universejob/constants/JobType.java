package space.yurisi.universecorev2.subplugins.universejob.constants;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.exception.JobTypeNotFoundException;

import java.util.List;

public enum JobType {

    NEET(0, 0, "無職", "定まった職がないこと。[類語]免職・解任・解職・罷免・無業・定職・現職・失職・離職・失業・解雇・馘首・首切り・くび・食い上げ・お払い箱・食いはぐれる・あぶれる・破門・お役御免・リストラ・免ずる・解く・暇を出す・暇を遣る・首になる・首を切る・首が飛ぶ コトバンクより", ItemStack.of(Material.LIME_BED)),

    LUMBERJACK(1, 0, "木こり", "チェストショップ・マーケットで木が売れた際に10%利益増加", ItemStack.of(Material.IRON_AXE)),
    MINER(2, 0, "採掘業", "チェストショップ・マーケットで鉱石、石が売れた際に10%利益増加", ItemStack.of(Material.IRON_PICKAXE)),
    CHEF(3, 0, "シェフ", "チェストショップ・マーケットで食料が売れた際に10%利益増加", ItemStack.of(Material.APPLE)),

    GLASSBLOWER(10, 100, "ガラス職人", "チェストショップ・マーケットでガラスが売れた際に10%利益増加", ItemStack.of(Material.GLASS)),
    BUILDER(11, 100, "建築士", "チェストショップ・マーケットで木、石関連を10%安く買える", ItemStack.of(Material.STONE)),

    CARPENTER(30, 300, "鳶職", "チェストショップ・マーケットで土、足場が売れた際に20%利益増加", ItemStack.of(Material.SCAFFOLDING)),
    ENGINEER(31, 300, "エンジニア", "チェストショップ・マーケットでレッドストーン関連を10%安く買える", ItemStack.of(Material.REDSTONE)),

    REAL_ESTATE_AGENT(50, 500, "不動産", "土地を10%安く買える", ItemStack.of(Material.OAK_SIGN)),

    RETAIL_WORKER(70, 700, "小売業", "チェストショップ・マーケットでガチャアイテムが売れた際に5%利益増加", ItemStack.of(Material.PAPER)),

    HIGH_LEVEL_MINER(100, 1000, "高度採掘業", "ネザーラックの採掘で1円入手", ItemStack.of(Material.NETHERRACK));

    private int id;
    private int level_cap;
    private String display_name;
    private String description;
    private ItemStack model;

    JobType(int id, int level_cap, String display_name, String description, ItemStack model) {
        this.id = id;
        this.level_cap = level_cap;
        this.display_name = "§a" + display_name;
        this.description = "§f" + description;
        this.model = model;
    }

    public static JobType getJobTypeFromID(int id) {
        for (JobType job : JobType.values()) {
            if (job.getId() == id) {
                return job;
            }
        }
        throw new JobTypeNotFoundException("JobType with ID " + id + " not found.");
    }

    public int getId() {
        return id;
    }

    public int getLevelCap() {
        return level_cap;
    }

    public String getDisplayName() {
        return display_name;
    }

    public String getDescription() {
        return description;
    }

    public ItemStack getModel() {
        return model;
    }

    public static List<Integer> getJobIDLowerThanLevel(int level) {
        return java.util.Arrays.stream(JobType.values())
                .filter(job -> job.getLevelCap() <= level)
                .map(JobType::getId)
                .sorted()
                .toList();
    }

}
