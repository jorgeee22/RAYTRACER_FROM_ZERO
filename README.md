# Raytracer From Zero

A simple raytracer implemented from scratch as part of a computer graphics project.  
The goal of this project is to demonstrate the fundamental principles behind ray tracing and how a virtual scene can be rendered by simulating the interaction between light rays and objects.

This project focuses on building the rendering pipeline manually instead of relying on external graphics libraries.

---

## Overview

Ray tracing is a rendering technique that simulates how rays of light travel through a scene and interact with objects to produce realistic images. Instead of rasterizing triangles like traditional graphics pipelines, ray tracing computes the color of each pixel by casting rays from a virtual camera into a 3D scene and determining how they intersect with objects.

In this project, a minimal raytracer was implemented from scratch to generate images of a 3D scene composed of basic geometric objects and lighting interactions.

---

## What This Project Produces

The program renders a synthetic image generated entirely through ray tracing.

The resulting image represents a virtual 3D scene where:

- Rays are emitted from a camera toward each pixel of the image
- Each ray travels through the scene until it intersects an object
- Lighting calculations determine the color of the pixel
- The final image is produced by combining all pixel calculations

The rendered output demonstrates how geometric objects and light sources interact to create shading, shadows, and perspective in a fully simulated environment.

This allows the program to produce images that resemble real-world lighting behavior without using traditional graphics pipelines.

---

## Key Concepts Demonstrated

This project illustrates several core ideas used in computer graphics:

- Ray casting from a virtual camera
- Intersection tests between rays and objects
- Surface normal calculations
- Lighting and shading models
- Scene representation using mathematical primitives

These principles are the foundation of modern rendering systems used in visual effects, animation, and real-time rendering engines.

---

## Technical Details

The raytracer implements a basic rendering pipeline including:

### 1. Ray Generation

For each pixel in the image plane, a ray is generated from the camera origin through the pixel location in the virtual screen space.

This ray represents the path that light would travel from the scene toward the viewer.

---

### 2. Ray–Object Intersection

The program calculates intersections between rays and objects in the scene.

Typical intersection tests include:

- Ray–sphere intersection
- Distance calculations
- Determining the closest visible surface

The closest intersection determines which object contributes to the final pixel color.

---

### 3. Surface Normals

Once an intersection point is found, the surface normal is computed.  
Surface normals are essential for calculating how light interacts with the surface.

---

### 4. Lighting Computation

The shading of each pixel is determined by evaluating the interaction between:

- Surface normals
- Light sources
- Camera position

These calculations determine the final color contribution for the pixel.

---

## Project Structure

Typical components of the raytracer include:

Each component is responsible for a specific part of the rendering process.

| Module | Purpose |
|------|------|
| Camera | Generates rays for each pixel |
| Ray | Represents the ray direction and origin |
| Objects | Defines geometric primitives in the scene |
| Renderer | Controls the rendering process |
| Scene | Contains objects and lighting information |

---

## Why This Project Matters

Ray tracing is widely used in modern graphics applications including:

- Film and animation rendering
- Scientific visualization
- Game engines
- Photorealistic rendering systems

Modern GPUs now include dedicated hardware for ray tracing, enabling realistic lighting effects such as reflections, refractions, and global illumination.

Implementing a raytracer from scratch is a valuable way to understand the mathematics and algorithms behind these technologies.

---

## Learning Outcomes

Through this project, the following concepts were explored:

- 3D vector mathematics
- Geometric intersections
- Rendering pipelines
- Lighting models
- Computer graphics fundamentals

The project demonstrates how relatively simple mathematical models can generate complex visual results.

---

## Possible Improvements

Future enhancements to this project could include:

- reflections
- refractions
- multiple light sources
- anti-aliasing
- texture mapping
- acceleration structures (BVH)

These improvements would significantly increase realism and performance.

---

## Made by

Jorge Enrique Ruiz Liera  
Computer Systems and Graphics Engineering

## Acknowledgements

Parts of this project were developed based on guidance and reference code provided by Professor Jafet Rodriguez.  
The implementation and extensions were completed as part of a Computer Graphics course.
