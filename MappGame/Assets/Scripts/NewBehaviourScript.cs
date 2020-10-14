using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class NewBehaviourScript : MonoBehaviour
{

    private GameObject player;
    private AudioSource playerAudioSource;
    private bool invincible = false;

    public float velocityX = 2.0f;
    public float velocityY = 5.0f;

    public float contactDmg;
    public float bulletDmg;
    public float dumb;
    public float TentacleDmg;

    public Vector3 playerPos;
    public GameObject bullet;
    Vector2 bulletPos;
    public float fireRate = 0.5f;
    public float nextFire = 0;
    public float health = 100;
    public float fullHealth;
    public float lives = 3;
    public AudioClip PowerupSound;
    public Transform RespawnPoint;

    private CanvasController canvasController;

    void Start()
    {
        playerAudioSource = GetComponent<AudioSource>();
        fullHealth = health;
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

    void Update()
    {

        

        if (Input.GetKey("w"))
        {
            transform.position += (Vector3.up * velocityY * Time.deltaTime);
        }
        if (Input.GetKey("s"))
        {
            transform.position += (Vector3.down * velocityY * Time.deltaTime);
        }

        if (Input.GetKey("h") && Time.time > nextFire)
        {
            nextFire = Time.time + fireRate;
            fire();
        }

        if (Input.GetKey("d"))
        {
            transform.position += ((Vector3.right * 8) * velocityX * Time.deltaTime);
        }

        if (Input.GetKey("a"))
        {
            transform.position += (Vector3.left * (velocityX * 8) * Time.deltaTime);
        }

    }
    public void fire()
    {
        playerPos = transform.position;
        bulletPos = playerPos;
        bulletPos += new Vector2(1.8f, 0.0f);
        Instantiate(bullet, bulletPos, Quaternion.identity);

    }

    public void Harm(float dmg)
    {
        // make the player blink
        if (health > 0 && !invincible)
        {
            health -= dmg;
            StartCoroutine("HurtColor");
            invincible = true;
            Invoke("resetInvulnerability", 0.5f);
        }
        else if (health <= 0)
        {
            // play animation of destroyed ship
            Destroy(gameObject);
            canvasController.GameOver();
            //  Application.LoadLevel("MainMenu");
            //lives--;
            //health = 1;
            //StartCoroutine(RespawnTime(3));
        }
    }

    IEnumerator RespawnTime(float time)
    {
        yield return new WaitForSeconds(time);
        if (lives == 0)
        {
            // show a game over menu
        }
        else
        {
            Respawn();
        }
    }

    public void Respawn()
    {

        canvasController.GameOver ();
        //Application.LoadLevel("MainMenu");
        //  transform.position = RespawnPoint.position;
        // health = fullHealth;
    }


    private void OnTriggerEnter2D(Collider2D other)
    {
        if (other.gameObject.CompareTag("Powerup"))
        {
            other.gameObject.SetActive(false);
            playerAudioSource.pitch = Random.Range(0.5f, 1.5f);
            playerAudioSource.PlayOneShot(PowerupSound, 0.5f);
            health += 50;
        }else if (other.gameObject.tag == "bullet" && !invincible)
        {
            StartCoroutine("HurtColor");
            health -= bulletDmg;
            invincible = true;
            Invoke("resetInvulnerability", 0.5f);
        }
    }
    private void OnCollisionEnter2D(Collision2D collision)
    {
        if (collision.gameObject.tag == "bullet" && !invincible)
        {
            StartCoroutine("HurtColor");
            health -= bulletDmg;
            invincible = true;
            Invoke("resetInvulnerability", 0.5f);
        }else if (collision.gameObject.tag == "Tentacle" && !invincible)
        {
            StartCoroutine("HurtColor");
            health -= TentacleDmg;
            invincible = true;
            Invoke("resetInvulnerability", 0.5f);
        }
    }

    void ShitHitsFan()
    {
        SceneManager.LoadScene("MainMenu");

    }


    void resetInvulnerability()
    {
        invincible = false;
    }
    IEnumerator HurtColor()
    {
        for (int i = 0; i < 3; i++)
        {
            GetComponent<SpriteRenderer>().color = new Color(1f, 1f, 1f, 0.3f); //Red, Green, Blue, Alpha/Transparency
            yield return new WaitForSeconds(.1f);
            GetComponent<SpriteRenderer>().color = Color.white; //White is the default "color" for the sprite, if you're curious.
            yield return new WaitForSeconds(.1f);
        }
    }
}

