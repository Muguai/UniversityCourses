using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Background_Parallax : MonoBehaviour {

    private Transform cam;
    private Vector3 previousCameraPosition;

    public Transform[] backgrounds;
    public float parallaxScale;
    public float parallaxReductionFactor;
    public float smoothing;

    void Awake()
    {
        cam = Camera.main.transform;
    }

    void Start ()
    {
        previousCameraPosition = cam.position;
	}
	
	void Update ()
    {
        float parallax = (previousCameraPosition.x - cam.position.x) * parallaxScale;

        for (int i = 0; i < backgrounds.Length; i++)
        {
            float backgroundTargetPositionX = backgrounds[i].position.x + parallax * (i * parallaxReductionFactor + 1);
            Vector3 backgroundTargetPosition = new Vector3(backgroundTargetPositionX, backgrounds[i].position.y, backgrounds[i].position.z);
            backgrounds[i].position = Vector3.Lerp(backgrounds[i].position, backgroundTargetPosition, smoothing * Time.deltaTime);
        }

        previousCameraPosition = cam.position;
            
	}
}
