OSMinecraft
===========

Projet utilisant OpenStreetMap pour créer une ville dans Minecraft.


Mode d'emploi
--------------
Il est supposé que vous savez déjà utiliser craftbukkit (configuration et lancement).

 1. Exporter une map depuis openstreetmap.org
 2. La mettre à la racine du projet et lancer la compilation
 3. Un fichier serialisé est alors créé, il faut deplacer ce fichier dans un dossier Mondes qui doit se trouver dans le dossier plugins du serveur
 4. Editer le fichier bukkit.yml pour y ajouter a la fin les lignes suivantes: worlds: nomDuMonde: generator: PluginGenerator: X
 5. X etant le chiffre qui se trouve a la fin du nom du fichier sérialisé (ex: SerializedWorld2)
 6. Placer le fichier PluginGenerator.jar dans le dossier plugins du serveur
 7. Lancer le serveur, ne pas oublier de supprimer l'ancien monde sinon craftbukkit lancera ce dernier a la place
 8. Enfin vous pouvez visiter la ville que vous avez choisi sur openstreetmap

