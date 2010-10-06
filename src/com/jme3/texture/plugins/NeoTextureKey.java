/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jme3.texture.plugins;

import com.jme3.asset.TextureKey;

/**
 *
 * @author normenhansen
 */
public class NeoTextureKey extends TextureKey{
    private int resolution = 1024;
    private String textureName = "texture";
    private boolean useCache = true;

    public NeoTextureKey(String name) {
        super(name);
    }

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

    @Override
    public Object postProcess(Object asset) {
        return asset;
    }

}
