using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BouncyEnemy : MonoBehaviour
{

    public float delta = 3.0f;  // Amount to move left and right from the start point
    public float speed = 2.0f;
    public float moveSpeed = -0.09f;
    private Vector3 startPos;
    private Vector3 v;
    public float dmg;

    void Start()
    {
        v = transform.position;
       
    }

    void Update()
    {


        v.y += delta * Mathf.Sin(Time.time * speed);
        v.x += moveSpeed;
        transform.position = v;
        transform.Translate(-Vector3.right * speed * Time.deltaTime);
        v.y -= delta * Mathf.Sin(Time.time * speed);
        //transform.position += Vector3.left * Time.deltaTime * 1.2f;




    }
    private void OnTriggerEnter2D(Collider2D other)
    {
        if (other.CompareTag("Player"))
        {
            other.GetComponent<NewBehaviourScript>().Harm(dmg);
            
        }
    }


}
