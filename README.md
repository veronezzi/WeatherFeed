# WeatherFeed 🌤️

App Android de previsão do tempo moderno, desenvolvido com **Jetpack Compose**, **Clean Architecture** e a [WeatherFeed Design System](https://github.com/veronezzi/weatherfeed-design-system).

---

## Screenshots

| Clima | 5 Dias | Buscar | Configurações |
|-------|--------|--------|---------------|
| Tela principal com temperatura e condições atuais | Previsão dos próximos 5 dias | Busca de cidades em tempo real | Unidade de temperatura persistida |

---

## Tecnologias Utilizadas

| Camada | Tecnologia |
|--------|------------|
| **Linguagem** | Kotlin 2.2.10 |
| **UI** | Jetpack Compose + Material3 |
| **Design System** | [weatherfeed-design-system 1.1.0](https://github.com/veronezzi/weatherfeed-design-system) |
| **Arquitetura** | Clean Architecture + MVVM |
| **Navegação** | Navigation Compose 2.8.9 |
| **Injeção de Dependência** | Hilt 2.60 + KSP |
| **Rede** | Retrofit 2.11.0 + OkHttp |
| **Serialização** | Gson |
| **Async** | Kotlin Coroutines + Flow |
| **Preferências** | DataStore Preferences |
| **Splash Screen** | Core Splashscreen API |
| **API de Clima** | [WeatherAPI.com](https://www.weatherapi.com) |
| **Build** | AGP 9.1.1 + Gradle 9.x |
| **Min SDK** | 24 (Android 7.0) |
| **Target SDK** | 37 (Android 15) |

---

## Arquitetura

O app segue **Clean Architecture** com separação clara em 3 camadas:

```
app/
├── data/
│   ├── remote/              # Retrofit API + DTOs + Condition Mapper
│   ├── repository/          # Implementação dos repositórios
│   └── preferences/         # DataStore para preferências do usuário
├── domain/
│   ├── model/               # Modelos de domínio puros (Kotlin)
│   ├── repository/          # Interfaces dos repositórios
│   └── usecase/             # Casos de uso
└── presentation/
    ├── navigation/          # NavGraph + rotas
    ├── common/              # UiState sealed interface
    ├── weather/             # Tela Clima + WeatherViewModel
    ├── forecast/            # Tela 5 Dias + ForecastViewModel
    ├── search/              # Tela Buscar + SearchViewModel
    └── settings/            # Tela Configurações + SettingsViewModel
```

**Padrão de estado:** `ViewModel` + `StateFlow` + `UiState<T>` (Loading / Success / Error)

---

## Funcionalidades

- **Clima atual** — temperatura, sensação térmica, umidade e vento
- **Previsão de 5 dias** — máxima/mínima por dia com condição
- **Busca de cidades** — autocomplete com debounce de 300ms via WeatherAPI
- **Seleção de cidade** — persiste a cidade selecionada via DataStore
- **Unidade de temperatura** — toggle °C / °F persistido entre sessões
- **Splash Screen** — Android 12+ Splashscreen API
- **Suporte Edge-to-Edge** — insets modernos do Android
- **Idioma** — resultados em Português Brasileiro via `lang=pt`

---

## Configuração

### 1. Obter API Key

1. Acesse [weatherapi.com](https://www.weatherapi.com)
2. Crie uma conta gratuita
3. Copie sua API key no dashboard

### 2. Adicionar ao projeto

No arquivo `local.properties` (na raiz do projeto), adicione:

```properties
WEATHER_API_KEY=sua_key_aqui
```

> ⚠️ O arquivo `local.properties` está no `.gitignore` — sua key nunca será commitada.

### 3. Compilar

```bash
./gradlew assembleDebug
```

---

## Design System

O app utiliza exclusivamente a biblioteca [WeatherFeed Design System](https://github.com/veronezzi/weatherfeed-design-system) para todos os componentes de UI:

- `WeatherFeedTheme` — tema global (Material3 customizado)
- `WeatherTopBar` — barra superior com localização
- `WeatherBottomNav` + `defaultNavItems` — navegação inferior com 4 tabs
- `StatCard` + `WeatherStat` — cards de métricas (sensação, umidade, vento)
- `ForecastRow` + `ForecastDay` — linhas de previsão diária
- `WeatherSearchBar` — campo de busca estilizado
- `CityRow` — item de resultado de cidade
- `SettingsRow` + `TemperatureToggle` — configurações
- `SectionLabel` — labels de seção uppercase

Distribuída via **JitPack**: `com.github.veronezzi:weatherfeed-design-system:1.1.0`

---

## Licença

MIT
