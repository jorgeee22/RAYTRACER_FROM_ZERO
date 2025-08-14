package edu.up.isgc.cg.raytracer;

import edu.up.isgc.cg.raytracer.lights.DirectionalLight;
import edu.up.isgc.cg.raytracer.lights.Light;
import edu.up.isgc.cg.raytracer.lights.PointLight;
import edu.up.isgc.cg.raytracer.materials.Material;
import edu.up.isgc.cg.raytracer.objects.*;
import edu.up.isgc.cg.raytracer.tools.OBJReader;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class Raytracer {
    private static final int MAX_RECURSION = 5;
    public static void main(String[] args) {
        System.out.println(new Date());
        Scene scene01 = new Scene();

        Camera camera = new Camera(
                new Vector3D(0,-0.7 , -2), 60, 60, 500, 500, 0.6, 50.0
        );

        scene01.setCamera(camera);
        // Mats
        Material floorMat = new Material(new Color(160, 160, 160), new Color(220, 220, 220), 60.0, 0.35,0,1.0);
        Material redMat = new Material(Color.RED, new Color(220, 220, 220), 25.0, 0.2,0,0);
        Material blueMat = new Material(Color.BLUE, new Color(220, 220, 220), 15.0, 0.01,0,0);
        Material greenMat = new Material(Color.GREEN, Color.WHITE, 25.0, 0.4,0,0);

        Material cyanMat = new Material(new Color(0, 200, 255), new Color(230, 230, 230), 50.0, 0.1,0.2,1.0 );
        Material mirrorMat = new Material(new Color(255,255,255), new Color(255,255,255), 50.0, 0.9, 0.1, 1.0); // 80% reflectivo
        Material ceramicMat = new Material( new Color(245, 245, 240), new Color(255, 255, 250),100.0,0.5,0.5,1.5);

        Material mirrorWall = new Material(new Color(200, 180, 150), new Color(100, 100, 100), 20.0, 0.95, 0.0, 1.0);
        Material glassMat = new Material(new Color(200, 240, 255), Color.WHITE, 128.0,  0.10, 0.90, 1.5);
        Material woodMat = new Material(new Color(103,103, 103),new Color(76,76,76),30,0.05,0.0,0);
        Material maderaMate = new Material(new Color(139, 110, 75), new Color(40, 30, 20), 10.0, 0.05, 0.0, 1.0);
        Material wallMat = new Material(new Color(200, 180, 150), new Color(100, 100, 100), 10.0, 0.0, 0.0, 1.0);




        // Lights
       scene01.addLight(new DirectionalLight(new Vector3D(0.5, -1, 0.5),Color.WHITE, 0.6));
        scene01.addLight(new DirectionalLight (new Vector3D(-0.3, -0.5, -0.8),new Color(200, 220, 255), 0.3));
        scene01.addLight(new PointLight(new Vector3D(0,1.5,2), new Color(255, 250, 240), 0.8));
        scene01.addLight(new PointLight(new Vector3D(-2, 0, 1), new Color(180, 220, 255), 0.5));




        // Objects
        scene01.addObject(new Plane(new Vector3D(0, -2.5, 0), floorMat,8, new Vector3D(0, 1, 0)));
        scene01.addObject(new Plane(new Vector3D(0, 2.5, 0), wallMat, 10, new Vector3D(0, -1, 0)));
        scene01.addObject(new Plane(new Vector3D(0, 0, 5), mirrorWall, 10, new Vector3D(0, 0, -1)));
        scene01.addObject(new Plane(new Vector3D(-5, 0, 0), wallMat, 10, new Vector3D(1, 0, 0)));
        scene01.addObject(new Plane(new Vector3D(5, 0, 0), wallMat, 10, new Vector3D(-1, 0, 0)));


        //scene01.addObject(new Sphere(new Vector3D(0, -1, 0), 0.3, mirrorMat));  // objeto para reflexion

   //    scene01.addObject(new Sphere(new Vector3D(-2.5, -1.5,-2 ), 1.0, glassMat)); //objeto para refraccion
      //  scene01.addObject(new Sphere(new Vector3D(0, -2, 3), 0.5, redMat));  // objeto de prueba


        // ObjObjects

     scene01.addObject(OBJReader.getModel3D("desk3.obj",new Vector3D(0,-2.5,0), maderaMate));
        scene01.addObject(OBJReader.getModel3D("glass.obj",new Vector3D(0,-1.2,-0.5), ceramicMat));
        scene01.addObject(OBJReader.getModel3D("glass.obj",new Vector3D(0.5,-1.2,-0.3), cyanMat));
        scene01.addObject(OBJReader.getModel3D("glass.obj",new Vector3D(-0.5,-1.2,-0.3), mirrorMat));
      // scene01.addObject(OBJReader.getModel3D("wine.obj",new Vector3D(0,-1,-0.5), ceramicMat));




      //  scene01.addObject(OBJReader.getModel3D("SmallTeapot.obj",new Vector3D(-2,-2.5,0), greenMat));

     //   scene01.addObject(OBJReader.getModel3D("SmallTeapot.obj",new Vector3D(0,-2.5,0), woodMat));
     //
        //scene01.addObject(OBJReader.getModel3D("SmallTeapot.obj",new Vector3D(0,-2.5,-2), glassMat));
       // scene01.addObject(OBJReader.getModel3D("SmallTeapot.obj",new Vector3D(-2,-2.5,0), cyanMat));


        BufferedImage image = raytrace(scene01);
        File outputImage = new File("image.png");
        try {
            ImageIO.write(image, "png", outputImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(new Date());
    }

    public static BufferedImage raytrace(Scene scene) {
        Camera mainCamera = scene.getCamera();
        double[] nearFarPlanes = mainCamera.getNearFarPlanes();
        BufferedImage image = new BufferedImage(mainCamera.getResolutionWidth(), mainCamera.getResolutionHeight(), BufferedImage.TYPE_INT_RGB);
        List<Object3D> objects = scene.getObjects();
        List<Light> lights = scene.getLights();
        Vector3D[][] posRaytrace = mainCamera.calculatePositionsToRay();
        Vector3D pos = mainCamera.getPosition();
        double cameraZ = pos.getZ();

        for (int i = 0; i < posRaytrace.length; i++) {
            for (int j = 0; j < posRaytrace[i].length; j++) {
                double x = posRaytrace[i][j].getX() + pos.getX();
                double y = posRaytrace[i][j].getY() + pos.getY();
                double z = posRaytrace[i][j].getZ() + pos.getZ();

                Ray ray = new Ray(mainCamera.getPosition(), new Vector3D(x, y, z));
                Intersection closestIntersection = raycast(ray, objects, null,
                        new double[]{cameraZ + nearFarPlanes[0], cameraZ + nearFarPlanes[1]});


                Color pixelColor = Color.BLACK;
                if (closestIntersection != null) {
                    pixelColor = traceRay(ray, scene, MAX_RECURSION,
                            objects, lights, cameraZ, nearFarPlanes);
                }
                image.setRGB(i, j, pixelColor.getRGB());
            }
        }
        return image;
    }

    private static Color traceRay(Ray ray, Scene scene, int depth, List<Object3D> objects, List<Light> lights, double cameraZ, double[] nearFarPlanes) {
        Intersection closestIntersection = raycast(ray, objects, null,
                new double[]{cameraZ + nearFarPlanes[0], cameraZ + nearFarPlanes[1]});
        if (closestIntersection == null) {
            return new Color(30, 30, 30);
        }
        Material material = closestIntersection.getObject().getMaterial();
        Color diffuseColor = material.getDiffuseColor();
        Color specularColor = material.getSpecularColor();
        double shininess = material.getShininess();
        Vector3D position = closestIntersection.getPosition();
        Vector3D normal = closestIntersection.getNormal();
        Vector3D viewDir = Vector3D.normalize(Vector3D.substract(scene.getCamera().getPosition(), closestIntersection.getPosition()));
        Color localColor = Color.BLACK;
        for (Light light : lights) {
            double nDotL = light.getNDotL(closestIntersection);
            Color lightColor = light.getColor();
            double intensity = light.getIntensity();

            // Shadow check
            boolean inShadow = false;
            Vector3D shadowOrigin = Vector3D.add(
                    closestIntersection.getPosition(),
                    Vector3D.scalarMultiplication(closestIntersection.getNormal(), 0.001)
            );

            Vector3D lightDir;
            if (light instanceof DirectionalLight) {
                lightDir = Vector3D.scalarMultiplication(
                        ((DirectionalLight) light).getDirection(), -1.0);
            } else {
                lightDir = Vector3D.normalize(
                        Vector3D.substract(light.getPosition(), closestIntersection.getPosition())
                );
            }

            Ray shadowRay = new Ray(shadowOrigin, lightDir);
            Intersection shadowIntersection = raycast(
                    shadowRay,
                    objects,
                    closestIntersection.getObject(),
                    null
            );

            if (shadowIntersection != null) {
                if (light instanceof DirectionalLight) {
                    inShadow = true;
                } else {
                    double distanceToLight = Vector3D.magnitude(
                            Vector3D.substract(light.getPosition(), closestIntersection.getPosition()));
                    double shadowFactor = Math.min(1.0, shadowIntersection.getDistance() / distanceToLight);
                    inShadow = shadowFactor < 0.9; // Umbral ajustable
                }
            }

            if (!inShadow) {
                // Attenuation for point lights
                if (light instanceof PointLight) {
                    double distance = Vector3D.magnitude(
                            Vector3D.substract(light.getPosition(), closestIntersection.getPosition())
                    );
                    intensity *= 1.0 / (1.0 + 0.1 * distance + 0.01 * distance * distance);
                }

                // Diffuse component
                double[] diffuseComponents = calculateLightComponent(
                        diffuseColor, lightColor, intensity * nDotL
                );

                // Specular component (Blinn-Phong)
                Vector3D halfwayVec = Vector3D.normalize(
                        Vector3D.add(viewDir, lightDir)
                );
                double specIntensity = Math.pow(
                        Math.max(Vector3D.dotProduct(
                                closestIntersection.getNormal(), halfwayVec), 0.0),
                        shininess
                );

                double[] specularComponents = calculateLightComponent(
                        specularColor, lightColor, intensity * specIntensity
                );

                // Combine components
                Color finalColor = combineComponents(diffuseComponents, specularComponents);
                localColor = addColor(localColor, finalColor);
            } else {
                // Ambient shadow (20% of light)
                double[] ambientComponents = calculateLightComponent(
                        diffuseColor, lightColor, intensity * 0.2
                );
                localColor = addColor(localColor, new Color(
                        (float) ambientComponents[0],
                        (float) ambientComponents[1],
                        (float) ambientComponents[2]
                ));
            }
        }
        Color reflectedColor = new Color(0, 0, 0);
        if (material.getReflectivity() > 0 && depth > 0) {
            Vector3D reflectedDir = Vector3D.reflect(Vector3D.normalize(ray.getDirection()), closestIntersection.getNormal());

            Vector3D reflectedOrigin = Vector3D.add(position, Vector3D.scalarMultiplication(normal, 0.001));

            Ray reflectedRay = new Ray(reflectedOrigin, reflectedDir);
            reflectedColor = traceRay(reflectedRay, scene, depth - 1, objects, lights, cameraZ, nearFarPlanes);
        }

        Color refractedColor = new Color(0, 0, 0); ;
        if (material.getTransparency() > 0 && depth > 0) {

            Vector3D refractedDir = Vector3D.refract(Vector3D.normalize(ray.getDirection()),closestIntersection.getNormal(), 1.0, material.getRefractiveIndex());

            if (refractedDir != null) {

                Vector3D refractedOrigin = Vector3D.substract(position, Vector3D.scalarMultiplication(normal, 0.001)
                );

                Ray refractedRay = new Ray(refractedOrigin, refractedDir);
                refractedColor = traceRay(refractedRay, scene, depth - 1, objects, lights, cameraZ, nearFarPlanes);
            } else {
                System.out.println("Reflexión interna total");
            }
        }

        double reflectivity = material.getReflectivity();
        double transparency = material.getTransparency();
        double diffuseFactor = Math.max(0, 1.0 - reflectivity - transparency);

        return new Color((float) Math.min(1.0,(localColor.getRed()/255.0 * diffuseFactor) + (reflectedColor.getRed()/255.0 * reflectivity) + (refractedColor.getRed()/255.0 * transparency)),
                         (float) Math.min(1.0, (localColor.getGreen()/255.0 * diffuseFactor) + (reflectedColor.getGreen()/255.0 * reflectivity) + (refractedColor.getGreen()/255.0 * transparency)),
                         (float) Math.min(1.0, (localColor.getBlue()/255.0 * diffuseFactor) + (reflectedColor.getBlue()/255.0 * reflectivity) + (refractedColor.getBlue()/255.0 * transparency))
        );
    }
    private static Color combineComponents(double[] diffuse, double[] specular) {
        return new Color(
                (float) Math.clamp(diffuse[0] + specular[0], 0.0, 1.0),
                (float) Math.clamp(diffuse[1] + specular[1], 0.0, 1.0),
                (float) Math.clamp(diffuse[2] + specular[2], 0.0, 1.0)
        );
    }
    private static double[] calculateLightComponent(Color baseColor, Color lightColor, double intensity) {
        if (lightColor == null) {
            lightColor = Color.WHITE; // Valor por defecto seguro
        }
        return new double[]{

                (baseColor.getRed() / 255.0) * (lightColor.getRed() / 255.0) * intensity,
                (baseColor.getGreen() / 255.0) * (lightColor.getGreen() / 255.0) * intensity,
                (baseColor.getBlue() / 255.0) * (lightColor.getBlue() / 255.0) * intensity
        };
    }

    public static Intersection raycast(Ray ray, List<Object3D> objects, Object3D caster, double[] clippingPlanes) {
        Intersection closestIntersection = null;

        for (int i = 0; i < objects.size(); i++) {
            Object3D currObj = objects.get(i);
            if (caster == null || !currObj.equals(caster)) {
                Intersection intersection = currObj.getIntersection(ray);
                if (intersection != null) {
                    double distance = intersection.getDistance();
                    double intersectionZ = intersection.getPosition().getZ();

                    if (distance >= 0 &&
                            (closestIntersection == null || distance < closestIntersection.getDistance()) &&
                            (clippingPlanes == null || (intersectionZ >= clippingPlanes[0] && intersectionZ <= clippingPlanes[1]))) {
                        closestIntersection = intersection;
                    }
                }
            }
        }

        return closestIntersection;
    }

    public static Color addColor(Color original, Color otherColor){
        float red = (float) Math.clamp((original.getRed() / 255.0) + (otherColor.getRed() / 255.0), 0.0, 1.0);
        float green = (float) Math.clamp((original.getGreen() / 255.0) + (otherColor.getGreen() / 255.0), 0.0, 1.0);
        float blue = (float) Math.clamp((original.getBlue() / 255.0) + (otherColor.getBlue() / 255.0), 0.0, 1.0);
        return new Color(red, green, blue);
    }
}
