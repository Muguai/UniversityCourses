using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class test : MonoBehaviour {

	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
		
	}
    public Transform ebullet;
 
    public float projectileSpeed = 0.0f;
    public float delay = 0.0f;
 public float weaponSpeed = 0.0f;
 private int bulletCount = 0;
 // Current player variable
 private GameObject player = null;
 void OnTriggerEnter(Collider col)
    {
        if (col.gameObject.tag == "player")
        {
            // Store current player
            // NB this assume that you have only one player because it will overwrite second player
            player = col.gameObject;
            InvokeRepeating("Shoot", delay, weaponSpeed);
        }
    }
    void OnTriggerExit(Collider col)
    {
        if (col.gameObject.tag == "player")
        {
            // Remove reference
            player = null;
            CancelInvoke("Shoot");
        }
    }
    void Shoot()
    {
       Transform shoot = Instantiate(ebullet, transform.position, transform.rotation);
        // Set projectile velocity
        //shoot.rigidbody.velocity = (player.transform.position - transform.position).normalized * projectileSpeed;
    }
}
