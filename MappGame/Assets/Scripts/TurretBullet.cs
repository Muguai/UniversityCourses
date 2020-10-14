using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TurretBullet : MonoBehaviour
{
    public float speed;
    Vector2 _direction;
    bool isReady = false;
    public float dmg;
    // Use this for initialization
    void Start()
    {

    }

    public void SetDirection(Vector2 direction)
    {
        _direction = direction.normalized;
        isReady = true;
    }

    // Update is called once per frame
    void Update()
    {

        if (isReady)
        {
            Vector2 position = transform.position;
            position += _direction * speed * Time.deltaTime;
            transform.position = position;

        }
        Destroy(gameObject, 5.0f);


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
    private void OnCollisionEnter2D(Collision2D collision)
    {

      
        Destroy(gameObject);

        
    }
   
}

