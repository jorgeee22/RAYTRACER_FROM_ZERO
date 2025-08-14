package edu.up.isgc.cg.raytracer.lights;

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Ray;
import edu.up.isgc.cg.raytracer.Vector3D;
import edu.up.isgc.cg.raytracer.materials.Material;

import java.awt.*;

public class PointLight extends Light{
    public PointLight(Vector3D position, Color color, double intensity) {
        super(position, color, intensity);
    }

    @Override
    public double getNDotL(Intersection intersection) {
        Vector3D lightDirection = Vector3D.substract(getPosition(), intersection.getPosition());
        lightDirection = Vector3D.normalize(lightDirection);
        return Math.max(Vector3D.dotProduct(intersection.getNormal(), lightDirection), 0.0);
    }

    @Override
    public Intersection getIntersection(Ray ray) {
        return null;
    }
}
