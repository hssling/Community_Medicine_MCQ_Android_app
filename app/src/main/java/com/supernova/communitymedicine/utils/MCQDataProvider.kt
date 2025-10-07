package com.supernova.communitymedicine.utils

import com.supernova.communitymedicine.Constants
import com.supernova.communitymedicine.data.model.Question

object MCQDataProvider {

    fun getAllCommunityMedicineQuestions(): List<Question> {
        val questions = mutableListOf<Question>()

        // Add Epidemiology questions
        questions.addAll(getEpidemiologyQuestions())

        // Add Health Systems questions
        questions.addAll(getHealthSystemsQuestions())

        // Add Communicable Diseases questions
        questions.addAll(getCommunicableDiseasesQuestions())

        // Add Maternal and Child Health questions
        questions.addAll(getMaternalChildHealthQuestions())

        // Add Nutrition questions
        questions.addAll(getNutritionQuestions())

        // Add Environmental Health questions
        questions.addAll(getEnvironmentalHealthQuestions())

        // Add Non-Communicable Diseases questions
        questions.addAll(getNonCommunicableDiseasesQuestions())

        // Add Demography questions
        questions.addAll(getDemographyQuestions())

        // Add Biostatistics questions
        questions.addAll(getBiostatisticsQuestions())

        // Add Health Planning questions
        questions.addAll(getHealthPlanningQuestions())

        return questions
    }

    private fun getEpidemiologyQuestions() = listOf(
        Question(
            questionText = "Which of the following is NOT a component of the epidemiologic triad?",
            optionA = "Host",
            optionB = "Agent",
            optionC = "Environment",
            optionD = "Vector",
            correctAnswer = "D",
            explanation = "The epidemiologic triad consists of Agent, Host, and Environment. Vector is a mode of disease transmission, not a core component of the triad.",
            chapter = Constants.Categories.EPIDEMIOLOGY,
            difficulty = Constants.DIFFICULTY_EASY
        ),

        Question(
            questionText = "The population attributable risk (PAR) is calculated as:",
            optionA = "Incidence in exposed / Incidence in unexposed - 1 × 100",
            optionB = "Incidence in population / Incidence in exposed",
            optionC = "Proportion of disease in population due to exposure × Incidence",
            optionD = "Incidence in total population - Incidence in unexposed population",
            correctAnswer = "C",
            explanation = "PAR measures the proportion of disease in the population that could be eliminated if the exposure were removed completely.",
            chapter = Constants.Categories.EPIDEMIOLOGY,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "In a cohort study of smoking and lung cancer, which measure would you use to determine risk?",
            optionA = "Odds Ratio",
            optionB = "Relative Risk",
            optionC = "Attributable Risk",
            optionD = "Population Attributable Risk",
            correctAnswer = "B",
            explanation = "Relative Risk (RR) is used in cohort studies to measure the probability of disease in exposed vs unexposed groups.",
            chapter = Constants.Categories.EPIDEMIOLOGY,
            difficulty = Constants.DIFFICULTY_MEDIUM
        )
    )

    private fun getCommunicableDiseasesQuestions() = listOf(
        Question(
            questionText = "Which vaccine provides herd immunity protection against measles at what percentage?",
            optionA = "60%",
            optionB = "80%",
            optionC = "85%",
            optionD = "95%",
            correctAnswer = "D",
            explanation = "Measles vaccine provides herd immunity when 95% of the population is vaccinated.",
            chapter = Constants.Categories.COMMUNICABLE,
            difficulty = Constants.DIFFICULTY_EASY
        ),

        Question(
            questionText = "The incubation period of hepatitis B is:",
            optionA = "2-6 weeks",
            optionB = "1-3 months",
            optionC = "2-6 months",
            optionD = "6-12 months",
            correctAnswer = "A",
            explanation = "Hepatitis B has an incubation period of 2-6 weeks, though the replicative phase can be much longer.",
            chapter = Constants.Categories.COMMUNICABLE,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "Which of the following diseases is NOT transmitted by vectors?",
            optionA = "Malaria",
            optionB = "Dengue",
            optionC = "Hepatitis",
            optionD = "Japanese Encephalitis",
            correctAnswer = "C",
            explanation = "Hepatitis is transmitted through blood/blood products, not by vectors. Malaria, Dengue, and Japanese Encephalitis are all vector-borne diseases.",
            chapter = Constants.Categories.COMMUNICABLE,
            difficulty = Constants.DIFFICULTY_EASY
        )
    )

    private fun getMaternalChildHealthQuestions() = listOf(
        Question(
            questionText = "The safe period in menstrual cycle for contraception is:",
            optionA = "Day 1-7",
            optionB = "Day 8-14",
            optionC = "Day 15-21",
            optionD = "Day 22-28",
            correctAnswer = "B",
            explanation = "During ovulation (approximately day 8-14), the risk of conception is highest. Day 1 is the first day of menstrual bleeding.",
            chapter = Constants.Categories.MATERNAL_CHILD,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "The minimum birth spacing recommended by WHO is:",
            optionA = "6 months",
            optionB = "18 months",
            optionC = "24 months",
            optionD = "36 months",
            correctAnswer = "C",
            explanation = "WHO recommends a minimum interval of 24 months between births to reduce perinatal and infant death risks.",
            chapter = Constants.Categories.MATERNAL_CHILD,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "According to NFHS-5, India's Maternal Mortality Ratio (MMR) is:",
            optionA = "97 per 1,00,000 live births",
            optionB = "103 per 1,00,000 live births",
            optionC = "113 per 1,00,000 live births",
            optionD = "123 per 1,00,000 live births",
            correctAnswer = "B",
            explanation = "According to recent NFHS-5 and SRS data, India's MMR is approximately 103 per 1,00,000 live births.",
            chapter = Constants.Categories.MATERNAL_CHILD,
            difficulty = Constants.DIFFICULTY_HARD
        )
    )

    private fun getNutritionQuestions() = listOf(
        Question(
            questionText = "Which vitamin deficiency is associated with angular cheilitis?",
            optionA = "Vitamin A",
            optionB = "Vitamin B12",
            optionC = "Vitamin B2 (Riboflavin)",
            optionD = "Folic acid",
            correctAnswer = "C",
            explanation = "Angular cheilitis (cracks at corner of mouth) is characteristic of riboflavin (vitamin B2) deficiency.",
            chapter = Constants.Categories.NUTRITION,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "PEM stands for:",
            optionA = "Protein Energy Malnutrition",
            optionB = "Pediatric Enteral Malnutrition",
            optionC = "Protein Essential Metabolism",
            optionD = "Pediatric Energy Metabolism",
            correctAnswer = "A",
            explanation = "PEM (Protein Energy Malnutrition) includes kwashiorkor and marasmus, the most common forms of severe malnutrition in children.",
            chapter = Constants.Categories.NUTRITION,
            difficulty = Constants.DIFFICULTY_EASY
        ),

        Question(
            questionText = "The Gomez classification is used for:",
            optionA = "Vitamin deficiencies",
            optionB = "Protein energy malnutrition",
            optionC = "Micronutrient deficiencies",
            optionD = "Anemia",
            correctAnswer = "B",
            explanation = "Gomez classification grades severity of PEM in children based on percentage below expected weight for age.",
            chapter = Constants.Categories.NUTRITION,
            difficulty = Constants.DIFFICULTY_MEDIUM
        )
    )

    private fun getEnvironmentalHealthQuestions() = listOf(
        Question(
            questionText = "The maximum allowable limit of lead in drinking water as per WHO is:",
            optionA = "0.05 mg/L",
            optionB = "0.01 mg/L",
            optionC = "0.005 mg/L",
            optionD = "0.001 mg/L",
            correctAnswer = "B",
            explanation = "WHO guidelines set maximum allowable concentration of lead in drinking water at 0.01 mg/L.",
            chapter = Constants.Categories.ENVIRONMENTAL,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "Which is the primary route of arsenic poisoning?",
            optionA = "Inhalation",
            optionB = "Ingestion",
            optionC = "Skin absorption",
            optionD = "Injection",
            correctAnswer = "B",
            explanation = "Arsenic poisoning most commonly occurs through ingestion of contaminated water or food.",
            chapter = Constants.Categories.ENVIRONMENTAL,
            difficulty = Constants.DIFFICULTY_MEDIUM
        )
    )

    private fun getNonCommunicableDiseasesQuestions() = listOf(
        Question(
            questionText = "According to WHO, the leading cause of death globally is:",
            optionA = "Cardiovascular diseases",
            optionB = "Cancer",
            optionC = "Respiratory diseases",
            optionD = "Diabetes",
            correctAnswer = "A",
            explanation = "Cardiovascular diseases (heart disease and stroke) are the leading cause of death globally, accounting for nearly 18 million deaths annually.",
            chapter = Constants.Categories.NON_COMMUNICABLE,
            difficulty = Constants.DIFFICULTY_EASY
        ),

        Question(
            questionText = "The Ottawa Charter focuses on:",
            optionA = "Health promotion",
            optionB = "Disease prevention",
            optionC = "Environmental health",
            optionD = "Nutrition programs",
            correctAnswer = "A",
            explanation = "The Ottawa Charter (1986) provides a framework for health promotion with action areas like building healthy public policy and strengthening community action.",
            chapter = Constants.Categories.NON_COMMUNICABLE,
            difficulty = Constants.DIFFICULTY_MEDIUM
        )
    )

    private fun getDemographyQuestions() = listOf(
        Question(
            questionText = "India's current population growth rate is approximately:",
            optionA = "1.0%",
            optionB = "0.8%",
            optionC = "1.2%",
            optionD = "1.5%",
            correctAnswer = "C",
            explanation = "As per latest census data, India's population growth rate has declined to around 1.2-1.3% annually.",
            chapter = Constants.Categories.DEMOGRAPHY,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "The demographic transition theory explains:",
            optionA = "Population growth patterns",
            optionB = "Economic development",
            optionC = "Social structure changes",
            optionD = "Technological advancement",
            correctAnswer = "A",
            explanation = "Demographic transition theory describes the transition from high birth/high death rates to low birth/low death rates as societies develop economically.",
            chapter = Constants.Categories.DEMOGRAPHY,
            difficulty = Constants.DIFFICULTY_MEDIUM
        )
    )

    private fun getBiostatisticsQuestions() = listOf(
        Question(
            questionText = "The mean is preferred over mode when data is:",
            optionA = "Nominal",
            optionB = "Ordinal",
            optionC = "Interval",
            optionD = "Qualitative",
            correctAnswer = "C",
            explanation = "For interval and ratio data, mean is the most appropriate measure of central tendency.",
            chapter = Constants.Categories.BIOSTATISTICS,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "Type I error is:",
            optionA = "Accepting null hypothesis when it's false",
            optionB = "Rejecting null hypothesis when it's true",
            optionC = "Both statements true",
            optionD = "Neither statement true",
            correctAnswer = "B",
            explanation = "Type I error (α) is rejecting a true null hypothesis (false positive). Type II error (β) is accepting a false null hypothesis.",
            chapter = Constants.Categories.BIOSTATISTICS,
            difficulty = Constants.DIFFICULTY_MEDIUM
        )
    )

    private fun getHealthPlanningQuestions() = listOf(
        Question(
            questionText = "The '5 year plans' in India are formulated by:",
            optionA = "Ministry of Health",
            optionB = "Ministry of Finance",
            optionC = "Planning Commission/NITI Aayog",
            optionD = "Health Department",
            correctAnswer = "C",
            explanation = "Planning Commission (now NITI Aayog) formulates Five Year Plans which include health sector planning.",
            chapter = Constants.Categories.PLANNING,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "BHIM (Ayushman Bharat Health Infrastructure Mission) focuses on:",
            optionA = "Primary health care",
            optionB = "Health infrastructure strengthening",
            optionC = "Medical education",
            optionD = "Drug manufacturing",
            correctAnswer = "B",
            explanation = "Ayushman Bharat Health Infrastructure Mission (BHIM) aims to strengthen health infrastructure in rural and urban areas through comprehensive primary health care.",
            chapter = Constants.Categories.PLANNING,
            difficulty = Constants.DIFFICULTY_HARD
        )
    )

    private fun getHealthSystemsQuestions() = listOf(
        Question(
            questionText = "The Alma-Ata Declaration emphasizes:",
            optionA = "Primary Health Care",
            optionB = "Specialized medical care",
            optionC = "Hospital-based services",
            optionD = "Medical research",
            correctAnswer = "A",
            explanation = "The Alma-Ata Declaration (1978) emphasized Primary Health Care as the key to achieving 'Health for All by 2000'.",
            chapter = Constants.Categories.HEALTH_SYSTEMS,
            difficulty = Constants.DIFFICULTY_EASY
        ),

        Question(
            questionText = "ASHA (Accredited Social Health Activist) is:",
            optionA = "A government doctor",
            optionB = "A village health worker",
            optionC = "A medical school graduate",
            optionD = "A hospital administrator",
            correctAnswer = "B",
            explanation = "ASHA workers are trained community health workers providing primary health care services, health education, and act as a link between community and health system.",
            chapter = Constants.Categories.HEALTH_SYSTEMS,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "Which is India's oldest health programme?",
            optionA = "National Malaria Control Programme",
            optionB = "Universal Immunisation Programme",
            optionC = "National Tuberculosis Control Programme",
            optionD = "National AIDS Control Programme",
            correctAnswer = "A",
            explanation = "The National Malaria Control Programme (1953) is India's oldest public health programme, later evolved into National Vector Borne Disease Control Programme.",
            chapter = Constants.Categories.HEALTH_SYSTEMS,
            difficulty = Constants.DIFFICULTY_MEDIUM
        )
    )
}
