using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class FiringBomb : MonoBehaviour {


    private GameObject player;

    public float velocityX = 2.0f;
    public float velocityY = 5.0f;

    public Vector3 playerPos;
    public GameObject bomb;
    Vector2 bombPos;
    public float fireRate = 0.5f;
    public float nextFire = 0;
    public float shootWait;
    
    // Use this for initialization
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {

    }
    public void startFire()
    {
        playerPos = transform.position;
        nextFire = Time.time + fireRate;
        fireBomb();

    }
    public void fireBomb()
    {
        bombPos = playerPos;
        bombPos += new Vector2(1.0f, -1.6f);
        Instantiate(bomb, bombPos, Quaternion.identity);

    }
   
}
