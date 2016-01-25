package com.will_code_for_food.crucentralcoast.model.common.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by MasonJStevenson on 1/18/2016.
 * <p/>
 * Holds image data associated with a DatabaseObject
 */
public class ImageData {
    private String id;
    private String version;
    private String signature;
    private int width;
    private int height;
    private String format;
    private String resourceType;
    private String url;
    private String secureUrl;
    private JsonObject fields;

    //for testing
    public ImageData(String id, String version, String signature, int width, int height, String format, String resourceType, String url, String secureUrl) {
        this.id = id;
        this.version = version;
        this.signature = signature;
        this.width = width;
        this.height = height;
        this.format = format;
        this.resourceType = resourceType;
        this.url = url;
        this.secureUrl = secureUrl;
    }

    public ImageData(JsonElement imageElement) {

        if (imageElement.isJsonObject()) {
            fields = imageElement.getAsJsonObject();

            id = fields.get("public_id").getAsString();
            version = fields.get("version").getAsString();
            signature = fields.get("signature").getAsString();
            width = fields.get("width").getAsInt();
            height = fields.get("height").getAsInt();
            format = fields.get("format").getAsString();
            resourceType = fields.get("resource_type").getAsString();
            url = fields.get("url").getAsString();
            secureUrl = fields.get("secure_url").getAsString();
        }
    }

    public JsonObject getFields() {
        return fields;
    }

    public void setFields(JsonObject fields) {
        this.fields = fields;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getSecureUrl() {
        return secureUrl;
    }

    public void setSecureUrl(String secureUrl) {
        this.secureUrl = secureUrl;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public boolean equals(Object other) {
        ImageData otherImage;

        if (other instanceof ImageData) {
            otherImage = (ImageData) other;

            return this.id.equals(otherImage.getId()) &&
                    this.version.equals(otherImage.getVersion()) &&
                    this.signature.equals(otherImage.getSignature()) &&
                    this.width == otherImage.getWidth() &&
                    this.height == otherImage.getHeight() &&
                    this.format.equals(otherImage.getFormat()) &&
                    this.url.equals(otherImage.url) &&
                    this.secureUrl.equals(otherImage.getSecureUrl());
        }

        return false;
    }
}
