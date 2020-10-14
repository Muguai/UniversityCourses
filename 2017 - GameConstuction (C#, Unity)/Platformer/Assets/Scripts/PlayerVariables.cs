using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerVariables : MonoBehaviour {

	public Transform startPosition;
	public float health = 100;
	public GameObject coinParticles;
	public AudioClip coinPickup;

	private float damageTimer;
	private AudioSource myAudioSource;

	void Start () {
		health = 100;
		myAudioSource = GetComponent<AudioSource> ();
	}
	

	void Update () {
		damageTimer += Time.deltaTime;
		GameController.gameControllerInstance.playerHealth = health;
	}

	public void Harm(float dmg){
		if (damageTimer > 1.0f) {
			health -= dmg;
			damageTimer = 0;
			GameController.gameControllerInstance.ScreenShake ();
		}

		if (health < 1) {
			Respawn ();
		}
	}

	public void Respawn() {
		transform.position = startPosition.position;
		health = 100;
	}

	private void OnTriggerEnter2D(Collider2D other) {
		if(other.gameObject.CompareTag("Coin")){
			other.gameObject.SetActive(false);
			Instantiate (coinParticles, other.transform.position, Quaternion.identity);
			GameController.gameControllerInstance.coins++;
			myAudioSource.pitch = Random.Range (0.5f, 1.5f);
			myAudioSource.PlayOneShot (coinPickup, 0.5f);
		}
        if (other.gameObject.CompareTag("KillBox"))
        {
            
            GameController.gameControllerInstance.mobs++;
            
            
        }
	}

}
