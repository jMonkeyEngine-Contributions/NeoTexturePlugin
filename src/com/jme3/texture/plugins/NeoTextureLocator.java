/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.texture.plugins;

import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetKey;
import com.jme3.asset.AssetLocator;
import com.jme3.asset.AssetManager;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author normenhansen
 */
public class NeoTextureLocator implements AssetLocator {

    private String rootPath = "/";

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public AssetInfo locate(AssetManager manager, AssetKey key) {
        String textureName = "texture";
        String name = key.getName();
        String tgrName = "";
        String ext = key.getExtension();
        if (name.indexOf("?") > -1) {
            String[] strings = name.split("\\?");
            tgrName = strings[0] + "." + ext;
            textureName = strings[1].substring(0, strings[1].lastIndexOf('.'));
        } else {
            return null;
        }
        NeoTextureKey neoKey = new NeoTextureKey(tgrName);
        if (key instanceof NeoTextureKey) {
            NeoTextureKey orig = (NeoTextureKey) key;
            neoKey.setResolution(orig.getResolution());
            neoKey.setTextureName(orig.getTextureName());
            neoKey.setUseCache(orig.isUseCache());
        }
        neoKey.setTextureName(textureName);
        AssetInfo info = manager.locateAsset(neoKey);
        return info;
    }

}
