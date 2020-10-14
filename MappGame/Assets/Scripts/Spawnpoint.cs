using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;


public class Spawnpoint : MonoBehaviour
{
    public GameObject enemy;
    public float mytimer;
    bool spawnEnemy = false;
    // Use this for initializatio
    void Start()
    {

    }

    // Update is called once per frame
    void Update()
    {
        
        //InvokeRepeating(OnTriggerExit2D, spawnTime, 2f);

    }
    
     // GameObject enemySpawnPoint = GameObject.Find("EnemySpawn");
     // Instantiate(enemy, enemySpawnPoint.transform.position, enemySpawnPoint.transform.rotation);
        
    
    public void OnTriggerEnter2D(Collider2D other) {
        Debug.Log("Spawn Normal");
        if (other.CompareTag("EnemyShips"))
        {
            spawnEnemy = true;
            Instantiate(enemy, transform.position, transform.rotation);
        }
    }
}



