using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BulletRemove : MonoBehaviour {

    public float lifetime = 1.0f;

    // Use this for initialization
    void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {

        Awake();
        
    }
   void Awake()
    {
        Destroy(this, lifetime);  
    }
}
