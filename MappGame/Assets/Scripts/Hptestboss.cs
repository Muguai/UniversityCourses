using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Hptestboss : MonoBehaviour {

    private int hp;
    public int hpreal;
    public int scoreValue;
    private CanvasController canvasController;
    public float dmg;
    public Transform Tentaclelocation, TentaclelocationRoof;
    public GameObject TentacleAttackPhase2, TentacleAttackRoofPhase2, TentacleAttackGroundPhase3, TentacleAttackRoofPhase3, TentacleAttackGroundPhase4, TentacleAttackRoofPhase4;
    private bool UnlockComplete = false;
    private bool UnlockComplete2 = false;
    private bool UnlockComplete3 = false;
    public float StartPhase2WhenHp, StartPhase3WhenHp, StartPhase4WhenHp;

    // Use this for initialization
    void Start()
    {
        hp = hpreal;
        Debug.Log("hp = " + hp);
        GameObject gameControllerObject = GameObject.FindWithTag("CanvasController");
        if (gameControllerObject != null)
        {
            canvasController = gameControllerObject.GetComponent<CanvasController>();
        }
        if (canvasController == null)
        {
            Debug.Log("Cannot find 'GameController' script");
        }
       

    }

    // Update is called once per frame
    void Update()
    {
        if (hp < 0)
        {
            canvasController.addScore(scoreValue);
            Debug.Log("bullet hit");
            GameObject Boss = GameObject.FindGameObjectWithTag("Boss");
            Destroy(Boss);
        }
        else if (hp == StartPhase2WhenHp)
        {
            if (UnlockComplete == false)
            {
                Phase2();
                UnlockComplete = true;
            }
            
        }else if(hp == StartPhase3WhenHp)
        {
            if (UnlockComplete2 == false)
            {
                Phase3();
                UnlockComplete2 = true;
            }
        }
        else if (hp == StartPhase4WhenHp)
        {
            if (UnlockComplete3 == false)
            {
                Phase4();
                UnlockComplete3 = true;
            }
        }

        
    }
    private void OnCollisionEnter2D(Collision2D other) //enemy dies on contact with bullet
    {
        if (other.gameObject.tag == "bullet")
        {
            hp -= 1;
            Debug.Log("minus hp" + hp);

        }

        else if (other.gameObject.tag == "Bomb")
        {
            hp -= 5;
        }
        else if (other.gameObject.tag == "Player")
        {
            gameObject.GetComponent<NewBehaviourScript>().Harm(dmg);

        }
    }
    private void OnTriggerEnter2D(Collider2D other) //enemy dies on contact with bullet
    {
        if (other.gameObject.tag == "bullet")
        {
            hp -= 1;

            Debug.Log("minus hp Trigger");

        }

        else if (other.gameObject.tag == "Bomb")
        {
            hp -= 5;
        }
        else if (other.CompareTag("Player"))
        {
            other.GetComponent<NewBehaviourScript>().Harm(dmg);

        }
    }
    void Phase2()
    {
        Instantiate(TentacleAttackPhase2, Tentaclelocation.transform.position, Tentaclelocation.transform.rotation);
        Instantiate(TentacleAttackRoofPhase2, TentaclelocationRoof.transform.position, TentaclelocationRoof.transform.rotation);
    }
    void Phase3()
    {
        Destroy(GameObject.Find("GameController(Clone)"));
        Destroy(GameObject.Find("Tentacle Gamecontroller Roof(Clone)"));
        Instantiate(TentacleAttackGroundPhase3, Tentaclelocation.transform.position, Tentaclelocation.transform.rotation);
        Instantiate(TentacleAttackRoofPhase3, TentaclelocationRoof.transform.position, TentaclelocationRoof.transform.rotation);
    }
    void Phase4()
    {
        Destroy(GameObject.Find("Tentacle GameController ground Phase 3(Clone)"));
        Destroy(GameObject.Find("Tentacle Gamecontroller Roof Phase 3(Clone)"));
        Instantiate(TentacleAttackGroundPhase4, Tentaclelocation.transform.position, Tentaclelocation.transform.rotation);
        Instantiate(TentacleAttackRoofPhase4, TentaclelocationRoof.transform.position, TentaclelocationRoof.transform.rotation);
    }



}