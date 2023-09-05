###### THIS FILE IS NOT A FUNCTIONAL PART OF THE PROJECT, IT'S JUST A TEMPORARY NOTE TO REMEMBER THE MEANING OF EACH KEYWORD

# File system

**MCFileSystem** = Un dossier aillant basé sur le modèle type du dossier .minecraft

**mainMCFileSystem** = Le dossier .minecraft contenant la ***Updatable Config***

**baseMCFileSystem** = Le dossier .minecraft/config/configupdatehandler/base contenant la ***Base Config***

# Config

**Updatable config** = Ensemble des configs se trouvant déjà dans le .minecraft avant l'***Update***, et qui sera potentiellement modifiée pendant.

**Base Config** = La configuration qui sert de base pour les updates. Une partie de la ***Base Config*** sera appliquée sur la ***Updatable Config***

# Mod files

**ConfigUpdateHandler Config** = Configuration de ce mod

# Update

**Modpack Update** = Mise à jour du modpack en lui même qui met à jour les mods la ***Base Config*** et la ***ConfigUpdateHandler Config*** que le modpackeur à définis.

**Config Update** = Mise à jour de certaines configurations locales (***Updatable Config***) vers les valeurs de la ***Base Config***.

**Config Entry** = Une entrée de configuration dans un fichier de configuration. Elle doit pouvoir être associée à un ***Entry Path*** pour pouvoir obtenir sa valeur.

**Entry Path** = Un path vers une Config Entry dans un fichier.

**Entry Update Config** = Paths (celui du fichier relatif au MCFileSystem et ***Entry Path***) et règles d'appliquation d'une modification de ***Config Entry*** depuis la ***Base Config*** vers l'***Updatable config***.

**Entry Update Config List** = Liste de ***Entry Update Config*** à appliquer. Contenu dans un ***Entry Update Config File***.

**Entry Update Config File** = Fichier contenant une **Entry Update Config List**. Un fichier par version de modpack répertoriée dans le dossier configupdatehandler/versions_updates

# Versions

**Base version** = Version actuelle du modpack, celle vers laquelle il a été mis à jour en dernier

**Actual version** = Version of the local modpack config. If it differs from the base version, ***Config Update*** will happen on launch.
