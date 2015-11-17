package com.will_code_for_food.crucentralcoast.model.getInvolved;

/**
 * Created by MasonJStevenson on 11/16/2015.
 */
public class MinistryTeam {
    private String id;
    private MinistryTeam parentMinistry;
    private String description;
    private String name;

    public MinistryTeam(String newId, MinistryTeam newParentMinistry, String newDescription, String newName) {
        this.id = newId;
        this.parentMinistry = newParentMinistry;
        this.description = newDescription;
        this.name = newName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MinistryTeam getParentMinistry() {
        return parentMinistry;
    }

    public void setParentMinistry(MinistryTeam parentMinistry) {
        this.parentMinistry = parentMinistry;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
