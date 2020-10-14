using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AlienShipBullet : MonoBehaviour
{

	

    public float velocityX = 20.0f;
    public float velocityY = 0;
    public float dmg;
    Rigidbody2D rb;

    // Use this for initialization
    void Start()
    {
        //rb = GetComponent<Rigidbody2D>();
    }

    // Update is called once per frame
    void Update()
    {
        GetComponent<Rigidbody2D>().velocity = new Vector2(-velocityX, GetComponent<Rigidbody2D>().velocity.y);
        //rb.velocity = new Vector2(velocityX, velocityY);
        Destroy(gameObject, 20.0f);
    }
    private void OnCollisionEnter2D(Collision2D collision) //bullet dies on contact with wall
    {
        //if(collision.gameObject.tag == "wall")
        
        Destroy(gameObject);
    }
    private void OnCollisionEnter2D(Collider2D collision)
    {

        if (collision.CompareTag("Player"))
        {
            collision.GetComponent<NewBehaviourScript>().Harm(dmg);
            Destroy(gameObject);

        }


    }

    private void OnTriggerEnter2D(Collider2D collision)
    {

        if (collision.CompareTag("Player"))
        {
            collision.GetComponent<NewBehaviourScript>().Harm(dmg);
            Destroy(gameObject);

        }
        else
        {
            Destroy(gameObject);
        }


    }
}

