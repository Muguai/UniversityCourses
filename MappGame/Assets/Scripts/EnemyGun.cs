using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemyGun : MonoBehaviour
{
    public GameObject turretbullet;
    private float _lastShotTime = float.MinValue;
    public float fireRate = 1.5f;
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
            nextFire = Time.time + fireRate;
            FireEnemyBullet();
        }

        GameObject Player = GameObject.Find("Player");
        distance = Vector3.Distance(transform.position, Player.transform.position);

    }

    void FireEnemyBullet()
    {
        GameObject playerShip = GameObject.Find("Player");

        if (playerShip != null && distance < range && Time.time > _lastShotTime + (3.0f / fireRate))
        {

            GameObject bullet = (GameObject)Instantiate(turretbullet);
            bullet.transform.position = transform.position;

            Vector2 direction = playerShip.transform.position - bullet.transform.position;
            bullet.GetComponent<TurretBullet>().SetDirection(direction);
        }
    }

}
