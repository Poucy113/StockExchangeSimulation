<img align="right" src="./media/icon.png" height="200" width="200" />

# Stock Exchange Simulation

### Interface

Au lancement du jeu, un nom d'utilisateur vous sera demand√©. **ATTENTION !** C'est avec ce nom d'utilisateur que vous pourrez r√©cuperer votre sauvegarde, ne le perdez pas !

![LoginPanel](./media/login.png)
--------------------------------------
L'interface de jeu est principalement constitu√©e par un graphe :

![MainPanel](./media/mainpanel.png)

Et de deux boutons, le bouton d'achat et de vente
### But du jeu
Le principe est d'avoir le plus d'argent possible en achentant et en vendant des actions (comme un trader !).
Vous devrez pour cela acheter le moins cher possible et de vendre pour un maximum de LapiCoins  !

### Les entreprises / Business

Une grande partie du gameplay de ce jeu: **Les entreprises** !
Les Entreprises sont indÈpendantes et possËdent chacune leurs propres courbes et leurs propres taux.
Les actions ne sont **pas interchangeables** entre les entreprises. Par exemple, si vous possedez une action chez Fondactul, vous ne pouvez *pas* la vendre chez Axelapi !
Vous pouvez Ègalement modifier les entreprises:
1. Elles se trouvent dans le fichier `./saves/businesses.json`
2. Un JSONArray contenu dans le fichier les stoquent
3. Je *(Nous ?)* deconseillons de modifier ce fichier, vous pouvez [ajouter des entreprises](#ajouter-une-entreprise), mais je dÈconseille de modifier celles qui y sont dÈja actuellement

#### Ajouter une entreprise

Chaque entreprise comporte ces elements:
- Un **minimum**, le maximum de chute
- Un **maximum**, le maximum de remontÈ
- Un **nom**, le nom de l'entreprise *(Non, vraiment ?)*
- Le **maximum de mises ‡ jour**, l'entreprise sera mise ‡ jour ‡ chaque interval de cette valeur (en ms)

Chaque entreprise se prÈsente sous cette forme:

```
{
	"min":-4,
	"max-update-time":850,
	"max":4,
	"name":"Le jardin des lapins"
}
```

Les icones:
Chaque entreprise a une icone, elle est sauvegardÈ dans le dossier [./src/assets/](./src/assets/), sous cette forme:
- `<nom-en-minuscule-espaces-remplacÈs-par-des-tirets>-business-icon.png`

Plusieurs extentions sont disponnibles: **JPEG** (`.jpg`), **PNG** (`.png`), **GIF** (`.gif`) 
*(!!ATTENTION!! Les gifs ne sont pas fonctionnels avec Swing)*

### Les menus

*C'est quoi une bonne interface sans les bon vieux boutons ??*

#### Les boutons d'entreprises

![Enterprise](./media/enterprise1.png)

Ce menu vous permet de changer d'entreprises et d'en cr√©er / d'en supprimer.
Lors de la cr√©ation d'une entreprise, il vous sera demand√© un nom, une chute maximum (la valeur maximum de la descente de la courbe) et une augmentation maximum (la valeur maximum de la mont√©e de la courbe).

#### Les options

![Options](./media/options.png)

Ce menu-ci vous permet d'adapter les param√®tres √† votre guise !
Vous pouvez personnaliser l'intervale (l'espacement entre chaque trait de la courbe), ou bien fermer tout les pop-ups
