package edu.up.isgc.cg.raytracer.bvh;



import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Ray;
import edu.up.isgc.cg.raytracer.Vector3D;
import edu.up.isgc.cg.raytracer.objects.Object3D;

import java.util.ArrayList;
import java.util.List;


public class BVHNode {

    private final BoundingBox bounds;
    private final BVHNode left;
    private final BVHNode right;
    private final List<Object3D> objects;

    public BVHNode(List<Object3D> objects) {
        this.objects = objects;
        this.bounds = calculateBounds(objects);

        if (objects.size() > 3) { // Límite de objetos por nodo hoja
            List<List<Object3D>> splitLists = splitObjects(objects);
            this.left = new BVHNode(splitLists.get(0));
            this.right = new BVHNode(splitLists.get(1));
        } else {
            this.left = null;
            this.right = null;
        }
    }

    private BoundingBox calculateBounds(List<Object3D> objects) {
        Vector3D min = new  Vector3D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        Vector3D max = new Vector3D(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);

        for (Object3D obj : objects) {
            BoundingBox objBounds = obj.getBoundingBox();
            min = new Vector3D(
                    Math.min(min.getX(), objBounds.getMin().getX()),
                    Math.min(min.getY(), objBounds.getMin().getY()),
                    Math.min(min.getZ(), objBounds.getMin().getZ())
            );
            max = new Vector3D(
                    Math.max(max.getX(), objBounds.getMax().getX()),
                    Math.max(max.getY(), objBounds.getMax().getY()),
                    Math.max(max.getZ(), objBounds.getMax().getZ())
            );
        }
        return new BoundingBox(min, max);
    }

    public Intersection intersect(Ray ray) {
        if (!bounds.intersect(ray)) return null;

        if (left == null && right == null) { // Nodo hoja
            Intersection closest = null;
            for (Object3D obj : objects) {
                Intersection hit = obj.getIntersection(ray);
                if (hit != null && (closest == null || hit.getDistance() < closest.getDistance())) {
                    closest = hit;
                }
            }
            return closest;
        }

        Intersection leftHit = left.intersect(ray);
        Intersection rightHit = right.intersect(ray);

        return (leftHit == null) ? rightHit :
                (rightHit == null) ? leftHit :
                        (leftHit.getDistance() < rightHit.getDistance()) ? leftHit : rightHit;
    }

    private List<List<Object3D>> splitObjects(List<Object3D> objects) {
        Vector3D size = Vector3D.substract(bounds.getMax(), bounds.getMin());
        int axis = (size.getX() > size.getY() && size.getX() > size.getZ()) ? 0 :
                (size.getY() > size.getZ()) ? 1 : 2;

        objects.sort((o1, o2) -> {
            double center1 = (o1.getBoundingBox().getMin().get(axis) + o1.getBoundingBox().getMax().get(axis)) / 2;
            double center2 = (o2.getBoundingBox().getMin().get(axis) + o2.getBoundingBox().getMax().get(axis)) / 2;
            return Double.compare(center1, center2);
        });

        return List.of(
                new ArrayList<>(objects.subList(0, objects.size() / 2)),
                new ArrayList<>(objects.subList(objects.size() / 2, objects.size()))
        );
    }
}

