using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Booster : MonoBehaviour {
    public float BoosterSpeed = 0;
    public float Phase4Speed;
	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
       
    }
   
    private void OnTriggerEnter2D(Collider2D collision)
    {
        Debug.Log("boosterhit trigger enter");
        if (collision.CompareTag("Tentacle"))
        {
            float boosting = BoosterSpeed;
            Debug.Log("Hit booster YAAAAAAS");
            collision.GetComponent<TentacleScript>().Boost(boosting);
        }
       else if (collision.CompareTag("SpecialTentacle"))
        {
            float boosting = Phase4Speed;
            Debug.Log("Hit booster YAAAAAAS");
            collision.GetComponent<TentacleScript>().Boost(boosting);
        }


    }
    
}
