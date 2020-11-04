package dev.victorman.cribbageboard;

public class Scale {
    private float widthScale;

    private float heightScale;

    public void setWidthScale(float widthScale) {
        this.widthScale = widthScale;
    }

    public void setHeightScale(float heightScale) {
        this.heightScale = heightScale;
    }

    public float getWidthScale() {
        return widthScale;
    }

    public float getHeightScale() {
        return heightScale;
    }

    public Scale(float widthScale, float heightScale) {
        this.widthScale = widthScale;
        this.heightScale = heightScale;
    }
}
