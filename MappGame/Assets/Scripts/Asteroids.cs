using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Asteroids : MonoBehaviour {

    public float speed;
    public float dmg;
	
	void Update ()
    {
        GetComponent<Animator>().speed = speed;
	}

    private void OnTriggerStay2D(Collider2D other)
    {
        if (other.CompareTag("Player"))
        {
            other.GetComponent<NewBehaviourScript>().Harm(dmg);
            GetComponent<Animator>().Play("Asteroid", -1, 0f);
        }
    }
}
