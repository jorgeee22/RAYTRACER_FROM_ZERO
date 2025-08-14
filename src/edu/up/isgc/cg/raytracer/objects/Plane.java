package edu.up.isgc.cg.raytracer.objects;

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Ray;
import edu.up.isgc.cg.raytracer.Vector3D;
import edu.up.isgc.cg.raytracer.materials.Material;

public class Plane extends Object3D{

    private Vector3D normal;
    private double size;
    public Plane(Vector3D position, Material material, double size, Vector3D normal) {
        super(position, material);
        this.normal = Vector3D.normalize(normal);
        this.size = size;
    }

    public Intersection getIntersection(Ray ray) {
        double denom = Vector3D.dotProduct(normal, ray.getDirection());

        if (Math.abs(denom) > 0.0001) {
            Vector3D planeToRay = Vector3D.substract(getPosition(), ray.getOrigin());
            double t = Vector3D.dotProduct(planeToRay, normal) / denom;

            if (t >= 0) {
                Vector3D intersectionPoint = Vector3D.add(
                        ray.getOrigin(),
                        Vector3D.scalarMultiplication(ray.getDirection(), t)
                );

                // Verificar si el punto de intersección está dentro del tamaño del plano
                Vector3D localPoint = Vector3D.substract(intersectionPoint, getPosition());
                if (Math.abs(localPoint.getX()) <= size/2 &&
                        Math.abs(localPoint.getZ()) <= size/2) {

                    return new Intersection(intersectionPoint, t, normal, this);
                }
            }
        }
        return null;
    }
}

