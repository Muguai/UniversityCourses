using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BulletImpact : MonoBehaviour {

	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
        OnCollisionEnter();
	}
    private void OnCollisionEnter()
    {
        Destroy(gameObject);
    }
}
