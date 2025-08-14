package edu.up.isgc.cg.raytracer.objects;

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Ray;
import edu.up.isgc.cg.raytracer.Vector3D;
import edu.up.isgc.cg.raytracer.bvh.BoundingBox;
import edu.up.isgc.cg.raytracer.materials.Material;

import java.awt.*;

public abstract class Object3D implements IIntersectable{
   private Color color;
    private Vector3D position;

    private Material material;
    public Object3D(Vector3D position, Material material) {
        setPosition(position);
        setMaterial(material);
    }
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Vector3D getPosition() {
        return position;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    public abstract BoundingBox getBoundingBox();
}

