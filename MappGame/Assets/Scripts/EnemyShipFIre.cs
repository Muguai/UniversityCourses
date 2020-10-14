using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemyShipFIre : MonoBehaviour {

    public GameObject turretbullet;
    public GameObject shootsFired;
    private float _lastShotTime = float.MinValue;
    private float fireRate = 1.5f;
    private float nextFire = 0;
    float distance;
    public float range;
    private Transform target;
    //private  target1;

    // Use this for initialization
    void Start()
    {

        // target1 = GameObject.Find("Player").transform.position;
        //target = target1;

    }

    // Update is called once per frame
    void Update()
    {

        if (Time.time > nextFire)
        {
            GameObject fired = (GameObject)Instantiate(shootsFired);
            fired.transform.position = transform.position;
            Destroy(fired, 0.03f);
            nextFire = Time.time + fireRate;
            
            FireEnemyBullet();
        }

        GameObject Player = GameObject.Find("Player");

    }

    void FireEnemyBullet()
    {
        GameObject playerShip = GameObject.Find("Player");

        if (playerShip != null && distance < range && Time.time > _lastShotTime + (3.0f / fireRate))
        {

            GameObject bullet = (GameObject)Instantiate(turretbullet);
           
            bullet.transform.position = transform.position;
            
          
        }
    }

}
