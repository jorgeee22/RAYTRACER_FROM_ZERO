package edu.up.isgc.cg.raytracer.lights;

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Ray;
import edu.up.isgc.cg.raytracer.Vector3D;
import edu.up.isgc.cg.raytracer.materials.Material;
import edu.up.isgc.cg.raytracer.objects.Object3D;

import java.awt.*;

public abstract class Light extends Object3D {
    private double intensity;
    private Color lightColor;


    public Light(Vector3D position, Color color, double intensity) {
        super(position, new Material(color, color, 0, 0,0,0));
        setLightColor(color);
        setIntensity(intensity);
    }

    // Getters y Setters
    public Color getLightColor() {
        return lightColor;
    }

    public void setLightColor(Color lightColor) {
        if (lightColor == null) {
            throw new IllegalArgumentException("Light color cannot be null");
        }
        this.lightColor = lightColor;
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = Math.max(0, intensity);
    }

    // Método abstracto para cálculo de iluminación
    public abstract double getNDotL(Intersection intersection);

    @Override
    public Intersection getIntersection(Ray ray) {
        return new Intersection(Vector3D.ZERO(), -1, Vector3D.ZERO(), null);
    }
}