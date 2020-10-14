using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Stopper : MonoBehaviour
{

    public float BoosterSpeed = 0;
    public float boostLeft;
    // Use this for initialization
    void Start()
    {

    }

    // Update is called once per frame
    void Update()
    {

    }

    private void OnTriggerEnter2D(Collider2D collision)
    {
        Debug.Log("boosterhit trigger enter");
        if (collision.CompareTag("Tentacle"))
        {
            float boosting = BoosterSpeed;
            Debug.Log("Hit Stopper YAAAAAAy");
            collision.GetComponent<TentacleScript>().Stopper(boosting);
        }else if (collision.CompareTag("SpecialTentacle"))
        {
            float boosting = BoosterSpeed;
            Debug.Log("Hit Special YAAAAAAy");
            collision.GetComponent<TentacleScript>().Stopper(boosting);
            collision.GetComponent<TentacleScript>().Lefting(boostLeft);
        }


    }
}
