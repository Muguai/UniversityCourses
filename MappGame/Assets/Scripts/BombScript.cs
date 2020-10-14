using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BombScript : MonoBehaviour {
    public float velocitY;
    public float velocitX;
    public float rotationSpeed = -1f;
    public float goodrotationSpeed;
    private Vector3 v;
    public float angleX;
    public float angleY;
    float rotation = -1;
    public float speed;
    public Rigidbody2D rb2D;
    public float GradualSpeedIncrease;
    public float GradualSpeedIncrease2;
    // Use this for initialization
    void Start()
    {
        //rb = GetComponent<Rigidbody2D>();
        // v = transform.position;

        rb2D = GetComponent<Rigidbody2D>();
    }

    // Update is called once per frame
    void Update()
    {
        GetComponent<Rigidbody2D>().velocity = new Vector2(velocitX, velocitY);

        if (transform.rotation.z > -90)
        {
            rb2D.MoveRotation(rb2D.rotation + speed * Time.fixedDeltaTime);
        } else if (transform.rotation.z == -90)
        {
            rb2D.MoveRotation(rb2D.rotation + 0f * Time.fixedDeltaTime);
        }

        velocitY -= GradualSpeedIncrease;
        velocitX += GradualSpeedIncrease2;

        //if (rotationSpeed >= -90) {
        //  transform.Rotate(0, 0, rotationSpeed * Time.deltaTime);
        //}else if (transform.rotation.z <= -90)

        //{
        //  transform.Rotate(0, 0, goodrotationSpeed * Time.deltaTime);
        //}

       // if (transform.rotation.z >= -90)
       // {
       //     rotation = rotation + Time.deltaTime;
       //     transform.Rotate(new Vector3(0.0f, 0.0f, rotation));
       // } else {
        //    rotation = 0;
       // }
       // transform.Rotate(new Vector3(0.0f, 0.0f, rotation));


        //rotates 50 degrees per second around z axis



        //rb.velocity = new Vector2(velocityX, velocityY);
        //Destroy(gameObject, 3.0f);
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

