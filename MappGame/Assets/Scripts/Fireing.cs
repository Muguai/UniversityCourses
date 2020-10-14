using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Fireing : MonoBehaviour {
    private GameObject player;
    private AudioSource playerAudioSource;

    public float velocityX = 2.0f;
    public float velocityY = 5.0f;

    public Vector3 playerPos;
    public GameObject bullet;
    Vector2 bulletPos;
    public float fireRate = 0.5f;
    public float nextFire = 0;
    public float health = 100;
    public float lives = 3;
    public AudioClip PowerupSound;
    // Use this for initialization
    void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
      playerPos = transform.position;

    }
    public void startFire()
    {
        nextFire = Time.time + fireRate;
        fire();
    }
    public void fire()
    {
        bulletPos = playerPos;
        bulletPos += new Vector2(1.6f, 0.0f);
        Instantiate(bullet, bulletPos, Quaternion.identity);

    }
}
