using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class HPTestEnemyShip : MonoBehaviour
{
    private int hp;
    public int hpreal;
    public int scoreValue;
    private CanvasController canvasController;
    public GameObject explosionAlienship, alienshipGun, alienshipNose, alienshipWing;

    // Use this for initialization
    void Start()
    {
        GameObject gameControllerObject = GameObject.FindWithTag("CanvasController");
        if (gameControllerObject != null)
        {
            canvasController = gameControllerObject.GetComponent<CanvasController>();
        }
        if (canvasController == null)
        {
            Debug.Log("Cannot find 'GameController' script");
        }
        hp = hpreal;
    }

    // Update is called once per frame
    void Update()
    {
        if (hp <= 0)
        {
            canvasController.addScore(scoreValue);
            Debug.Log("bullet hit");
            Instantiate(explosionAlienship, transform.position, Quaternion.identity);
            Instantiate(alienshipGun, transform.position, Quaternion.identity);
            Instantiate(alienshipNose, transform.position, Quaternion.identity);
            Instantiate(alienshipWing, transform.position, Quaternion.identity);
            Destroy(gameObject);
        }
    }
    private void OnTriggerEnter2D(Collider2D other) //enemy dies on contact with bullet
    {
        if (other.gameObject.tag == "bullet")
        {
            hp -= 1;


        }
        else if (other.gameObject.tag == "Backwall")
        {
            hp -= 100;
            if (hp <= 0)
                Destroy(gameObject);

        }
        else if (other.gameObject.tag == "Bomb")
        {
            hp -= 5;

        }
    }



}

