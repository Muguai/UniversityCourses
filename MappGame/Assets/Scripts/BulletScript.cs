using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BulletScript : MonoBehaviour
{

    public float velocityX = 5.0f;
    public float velocityY = 0;
    Rigidbody2D rb;

    // Use this for initialization
    void Start()
    {
        //rb = GetComponent<Rigidbody2D>();
    }

    // Update is called once per frame
    void Update()
    {
        GetComponent<Rigidbody2D>().velocity = new Vector2(velocityX, GetComponent<Rigidbody2D>().velocity.y);
        //rb.velocity = new Vector2(velocityX, velocityY);
        Destroy(gameObject, 3.0f);
    }
    private void OnCollisionEnter2D(Collision2D collision) //bullet dies on contact with wall
    {
        //if(collision.gameObject.tag == "wall")
        Destroy(gameObject);
    }
    private void OnTriggerEnter2D(Collider2D other) //bullet dies on contact with wall
    {
        //if(collision.gameObject.tag == "wall")
        Destroy(gameObject);
    }
}
