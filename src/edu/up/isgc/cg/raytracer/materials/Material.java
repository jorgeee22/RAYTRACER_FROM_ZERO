package edu.up.isgc.cg.raytracer.materials;
import java.awt.Color;
public class Material {
    private Color diffuseColor;
    private Color specularColor;
    private double shininess;
    private double reflectivity;
    private double transparency;
    private double refractiveIndex;

    public double getTransparency() {
        return transparency;
    }

    public void setTransparency(double transparency) {
        this.transparency = transparency;
    }

    public double getRefractiveIndex() {
        return refractiveIndex;
    }

    public void setRefractiveIndex(double refractiveIndex) {
        this.refractiveIndex = refractiveIndex;
    }
    public Color getDiffuseColor() {
        return diffuseColor;
    }

    public void setDiffuseColor(Color diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    public Color getSpecularColor() {
        return specularColor;
    }

    public void setSpecularColor(Color specularColor) {
        this.specularColor = specularColor;
    }

    public double getShininess() {
        return shininess;
    }

    public void setShininess(double shininess) {
        this.shininess = shininess;
    }

    public double getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(double reflectivity) {
        this.reflectivity = reflectivity;
    }

    public Material(Color diffuseColor, Color specularColor, double shininess, double reflectivity, double transparency, double refractiveIndex) {
        setDiffuseColor(diffuseColor);
        setSpecularColor(specularColor);
        setShininess(shininess);
        setReflectivity(reflectivity);
        setTransparency(transparency);
        setRefractiveIndex(refractiveIndex);
    }






}
