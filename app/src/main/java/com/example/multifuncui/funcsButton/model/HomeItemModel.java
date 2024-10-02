package com.example.multifuncui.funcsButton.model;

import com.example.multifuncui.R;
import com.example.multifuncui.funcsButton.model.enums.ActionType;
import com.example.multifuncui.funcsButton.model.enums.RoleFunc;
import com.example.multifuncui.funcsButton.model.enums.Type;

import java.util.Set;

public class HomeItemModel {
    private static int idCounter = 0;
    private int id;
    private int text;
    private int imageResource;
    private int color;
    private ActionType code;

    private boolean atHome;

    private Type type;
    private int desc;
    private Set<RoleFunc> roles;


    public HomeItemModel(int text, int imageResource, int color, ActionType code, boolean atHome, Type  type, int desc, Set<RoleFunc> roles) {
        this.id = ++idCounter;
        this.text = text;
        this.imageResource = imageResource;
        this.color = color;
        this.code = code;
        this.atHome = atHome;
        this.type = type;
        this.desc = desc;
        this.roles = roles;
    }

    public static int getColumnTitle(int index)
    {
        return switch (index) {
            case 0 -> R.string.searching;
            case 1 -> R.string.ai;
            case 2 -> R.string.size;
            case 3 -> R.string.all;
            case 4 -> R.string.favorite;
            case 5 -> R.string.mangement;
            case 6 -> R.string.action;
            default -> R.string.other;
        };
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getText() {
        return text;
    }

    public void setText(int text) {
        this.text = text;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public ActionType getCode() {
        return code;
    }

    public void setCode(ActionType code) {
        this.code = code;
    }

    public boolean isAtHome() {
        return atHome;
    }

    public void setAtHome(boolean atHome) {
        this.atHome = atHome;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getDesc() {
        return desc;
    }

    public void setDesc(int desc) {
        this.desc = desc;
    }

    public Set<RoleFunc> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleFunc> roles) {
        this.roles = roles;
    }


}
