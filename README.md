# Kronos Example

Esta repositorio es usada como propuesta de modelo para apps Kronos.


# Correr el Proyecto!

Para correr el proyecto solo tienes que seguir estos pasos:

  - Clonar el proyecto.
  - Tener Android studio 3.0 o superior con sdk version 25.
  - Build proyecto.
  - Correr el proyecto.
  - Listo! :)



### Estructura de Proyecto

La arquitectura utilizada esta basada en Modelo Vista Presentador (MVP), compuesta como:


> **data**: Contiene todos los modelos de datos.

> **di**: Contiene todos los componentes y modelos de dagger.

> **ui**: Contiene adapters, bases y todas las vistas de la app.

> **utils**: Contiene todos los utils del proyecto.


### Depencias usadas

* [Dagger 2](https://google.github.io/dagger/) - dependency injection framework!
* [Butterknife](http://jakewharton.github.io/butterknife/) - injection views framework!
* [RxJava](https://github.com/ReactiveX/RxJava) - a library for composing asynchronous and event-based.
* [RxAndroid](https://github.com/ReactiveX/RxAndroid) - Reactive Extensions for Android
* [Glide](https://github.com/bumptech/glide) - Glide is a fast and efficient open source media management and image loading framework for Android.
* [RxPermissions](https://github.com/tbruyelle/RxPermissions) - This library allows the usage of RxJava with the new Android M permission model.