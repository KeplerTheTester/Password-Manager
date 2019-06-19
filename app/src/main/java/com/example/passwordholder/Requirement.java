package com.example.passwordholder;

public class Requirement {
    String requirementType;

    public Requirement(String requirementType)
    {
        this.requirementType = requirementType;
    }

    public String getRequirementType() {
        switch (requirementType)
        {
            case "regular":
                return "regular";
            case "length10":
                return "length10";
            case "upperLower":
                return "upperLower";
            case "specialCharacters":
                return "specialCharacters";
            default:
                return "not a valid requirement";
        }
    }

}
