/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jme3.texture.plugins;

import com.jme3.asset.AssetKey;
import com.jme3.texture.Texture2D;

/**
 *
 * @author normenhansen
 */
public class NeoTextureKey extends AssetKey<Texture2D>{
    private int resolution = 1024;
    private String textureName = "texture";
    private boolean useCache = true;

    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }

    public String getTextureName() {
        return textureName;
    }

    public void setTextureName(String textureName) {
        this.textureName = textureName;
    }

    public boolean isUseCache() {
        return useCache;
    }

    public void setUseCache(boolean useCache) {
        this.useCache = useCache;
    }

}
