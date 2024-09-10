<!--
 * Copyright (c) 2021 CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-FileType: DOCUMENTATION
 * SPDX-FileCopyrightText: 2020 Eclipse Foundation
 * SPDX-License-Identifier: EPL-2.0
 *
 -->


# Eclipse Papyrus

* Eclipse Papyrus (http://www.eclipse.org/papyrus) is a model editor for UML2.5 and SysML1.6, fUML 1.2.1, ALF 1.0.1, MARTE 1.1, BPMNProfile 1.0, BMM 1.3, SMM 1.1, PSCS 1.0, PSSM 1.0b, FMI 2.0 and ISO / IEC 42010. As a result, it supports the diagram and table editors of these modeling languages by offering users full support for the underlying modeling concepts and as defined in the these standards. 
* Eclipse Papyrus also allows models to be exploited by employing model-based techniques through additional modules: model-based simulation, formal model-based testing, safety analysis, performance / tradeoff analysis, exploration of architecture...
* To address any specific area, each part of Eclipse Papyrus can be customized: UML profile, model explorer, diagram notation and style, property views, palette and creation menus and connection of new analysis module .


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and/or testing purposes.

### Prerequisites

The things you need before installing the software.

* Select an update site from the [latest releases] (https://projects.eclipse.org/projects/modeling.mdt.papyrus/downloads).
<!-- https://download.eclipse.org/modeling/mdt/papyrus/updates/releases/ -->
* Choose the Eclipse IDE compatible with the selected relase from the [latest releases] (https://www.eclipse.org/eclipseide/). If you do not see the required release, you can select previous ones by clicking on 'other packages'.
* The latest compatible version of the Eclipse IDE (you can see the latest published release [here](https://projects.eclipse.org/projects/modeling.mdt.papyrus)).
* A current version of Java 11 if you want Papyrus of 5+ and Java 1.8 for previous ones (jre if you only intend to use the tool and jdk if you intend to look at or modify the code). Openjdk is preferable but not required. 

### Installation

In order to install the software you will have to open the downloaded IDE and install it via the integrated installer.

```
$ Open the Help menu of your selected IDE.
$ Go to Install New Software.
$ Enter the URL of the selected Papyrus update site in the 'Work with' field and select the features to install. The default one is Papyrus for UML that will give you access to the tool.
$ Click on Next; the installer will automatically compute the required dependencies from the urls available.
$ Accept the licences of the tools you want to install and click on finish. You will need to restart the IDE to finalize the installation.
```

## Compile

Papyrus is meant to be compiled with maven. To do so you will need to fetch the git repository. It can be found [here] (https://git.eclipse.org/c/www.eclipse.org/papyrus.git/)

### Usefull commands

```
$ mvn clean verify -f <pathToSelectedPom>
```

## Contributing

You can contribute to the Eclipse Papyrus project via [Gerrit] (https://git.eclipse.org/r/). 
If you are interested, you can see a detailed sequence in [this page] (https://wiki.eclipse.org/Gerrit).
Contributions should be done based on the master branch in order to be automatically verified by its associated Jenkins job. 


## Additional Documentation and Acknowledgments

* Project folder on server: https://git.eclipse.org/c/papyrus/org.eclipse.papyrus.git
* News and documentation to contribute: https://wiki.eclipse.org/Papyrus
* Forum: https://www.eclipse.org/forums/index.php/f/121/
* Mailing list: https://accounts.eclipse.org/mailing-list/mdt-papyrus.dev