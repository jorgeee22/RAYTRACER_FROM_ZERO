package edu.up.isgc.cg.raytracer.bvh;

import edu.up.isgc.cg.raytracer.Ray;
import edu.up.isgc.cg.raytracer.Vector3D;
public class BoundingBox {
    private final Vector3D min;
    private final Vector3D max;


    public BoundingBox(Vector3D min, Vector3D max) {
        this.min = min;
        this.max = max;
    }
    public Vector3D getMin() { return min; }
    public Vector3D getMax() { return max; }

    public boolean intersect(Ray ray) {

        double tMin = (min.getX() - ray.getOrigin().getX()) / ray.getDirection().getX();
        double tMax = (max.getX() - ray.getOrigin().getX()) / ray.getDirection().getX();
        if (tMin > tMax) {
            double temp = tMin;
            tMin = tMax;
            tMax = temp;
        }

        double tyMin = (min.getY() - ray.getOrigin().getY()) / ray.getDirection().getY();
        double tyMax = (max.getY() - ray.getOrigin().getY()) / ray.getDirection().getY();
        if (tyMin > tyMax) {
            double temp = tyMin;
            tyMin = tyMax;
            tyMax = temp;
        }

        if (tMin > tyMax || tyMin > tMax) return false;
        if (tyMin > tMin) tMin = tyMin;
        if (tyMax < tMax) tMax = tyMax;

        double tzMin = (min.getZ() - ray.getOrigin().getZ()) / ray.getDirection().getZ();
        double tzMax = (max.getZ() - ray.getOrigin().getZ()) / ray.getDirection().getZ();
        if (tzMin > tzMax) {
            double temp = tzMin;
            tzMin = tzMax;
            tzMax = temp;
        }

        return !(tMin > tzMax || tzMin > tMax);
    }





}
