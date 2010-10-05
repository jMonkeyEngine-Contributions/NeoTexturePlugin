/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jme3.material.plugins;

import com.jme3.asset.AssetKey;
import com.jme3.material.Material;
import com.jme3.texture.Texture2D;

/**
 *
 * @author normenhansen
 */
public class NeoTextureMaterialKey extends AssetKey<Material>{
    private int resolution = 1024;
    private String materialDef = "Common/MatDefs/Light/Lighting.j3md";
    private Texture2D.WrapMode wrapMode=Texture2D.WrapMode.Repeat;

    public NeoTextureMaterialKey() {
    }

    public NeoTextureMaterialKey(String name) {
        super(name);
    }

    public int getResolution() {
        return resolution;
    }

    /**
     * Sets the resolution of the generated textures (default=1024)
     * @param resolution
     */
    public void setResolution(int resolution) {
        this.resolution = resolution;
    }

    public String getMaterialDef() {
        return materialDef;
    }

    /**
     * Sets the materialDef used for the generated material (default=Commons/MatDefs/Light/Lighting.j3md)
     * @param materialDef
     */
    public void setMaterialDef(String materialDef) {
        this.materialDef = materialDef;
    }

    /**
     * @return the wrapMode
     */
    public Texture2D.WrapMode getWrapMode() {
        return wrapMode;
    }

    /**
     * @param wrapMode the Texture wrapMode to set (default=Repeat)
     */
    public void setWrapMode(Texture2D.WrapMode wrapMode) {
        this.wrapMode = wrapMode;
    }

}
