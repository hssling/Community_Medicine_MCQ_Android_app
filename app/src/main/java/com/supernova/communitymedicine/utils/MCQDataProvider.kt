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

        // Add Nutrition from textbook content
        questions.addAll(getAdditionalNutritionQuestions())

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

    private fun getAdditionalNutritionQuestions() = listOf(
        // MCQs from edu_video_pipeline/nutrition_quiz_bank.txt
        Question(
            questionText = "During pregnancy, how many extra kilocalories are generally needed per day?",
            optionA = "100 kcal",
            optionB = "200 kcal",
            optionC = "300 kcal",
            optionD = "600 kcal",
            correctAnswer = "C",
            explanation = "Extra energy requirement for pregnant women in the second and third trimesters is around 300 kcal/day above the normal requirement.",
            chapter = Constants.Categories.NUTRITION,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "Which nutrient is most important for preventing anemia in pregnancy?",
            optionA = "Vitamin C",
            optionB = "Calcium",
            optionC = "Iron and folic acid",
            optionD = "Vitamin D",
            correctAnswer = "C",
            explanation = "Iron and folic acid are crucial for preventing anemia during pregnancy. Iron is needed for hemoglobin synthesis and folic acid prevents neural tube defects.",
            chapter = Constants.Categories.NUTRITION,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "A lactating mother requires approximately how many extra kilocalories per day?",
            optionA = "150 kcal",
            optionB = "300 kcal",
            optionC = "500 kcal",
            optionD = "700 kcal",
            correctAnswer = "C",
            explanation = "Breastfeeding mothers need about 500 extra calories per day to produce sufficient milk while maintaining their own health.",
            chapter = Constants.Categories.NUTRITION,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "Which food is a good source of calcium during pregnancy and lactation?",
            optionA = "Apple",
            optionB = "Rice",
            optionC = "Ragi (finger millet)",
            optionD = "Mustard oil",
            correctAnswer = "C",
            explanation = "Ragi (finger millet) is an excellent source of calcium, containing about 344 mg per 100g, making it ideal for bone health during pregnancy and lactation.",
            chapter = Constants.Categories.NUTRITION,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "What practice supports hydration for breastfeeding mothers?",
            optionA = "Drinking only tea and coffee",
            optionB = "Avoiding water before breastfeeding",
            optionC = "Drinking water before and after breastfeeding",
            optionD = "Restricting fluids",
            correctAnswer = "C",
            explanation = "Adequate hydration is essential for milk production. Mothers should drink water before, during, and after breastfeeding to stay hydrated.",
            chapter = Constants.Categories.NUTRITION,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        // Additional Community Medicine Nutrition MCQs
        Question(
            questionText = "Which micronutrient deficiency is most prevalent among Indian children under 5 years?",
            optionA = "Vitamin A",
            optionB = "Vitamin D",
            optionC = "Vitamin B12",
            optionD = "Folic acid",
            correctAnswer = "A",
            explanation = "Vitamin A deficiency is highly prevalent among Indian children, leading to increased susceptibility to infections and potential blindness (xerophthalmia).",
            chapter = Constants.Categories.NUTRITION,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "The Recommended Dietary Allowance (RDA) for energy in India is established by:",
            optionA = "WHO",
            optionB = "NIN (National Institute of Nutrition)",
            optionC = "ICMR",
            optionD = "All of the above",
            correctAnswer = "D",
            explanation = "RDAs in India are established jointly by ICMR, NIN (National Institute of Nutrition), and follow WHO guidelines.",
            chapter = Constants.Categories.NUTRITION,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "What is the primary cause of iron deficiency anemia in India?",
            optionA = "Blood loss",
            optionB = "Inadequate iron intake",
            optionC = "Malabsorption",
            optionD = "Chronic disease",
            correctAnswer = "B",
            explanation = "Iron deficiency anemia in India is primarily due to inadequate dietary intake of iron, compounded by low bioavailability from vegetarian diets.",
            chapter = Constants.Categories.NUTRITION,
            difficulty = Constants.DIFFICULTY_EASY
        ),

        Question(
            questionText = "Mid Day Meal (MDM) scheme in India was initially started to:",
            optionA = "Provide free meals to all school children",
            optionB = "Improve school enrollment",
            optionC = "Prevent child malnutrition",
            optionD = "Eradicate poverty",
            correctAnswer = "C",
            explanation = "The MDM scheme was started in Tamil Nadu in 1962 to prevent severe malnutrition and ensure children received at least one nutritious meal daily.",
            chapter = Constants.Categories.NUTRITION,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "Which chapter of Indian Penal Code deals with the prevention of food adulteration?",
            optionA = "IPC Section 272-273",
            optionB = "IPC Section 200-210",
            optionC = "IPC Section 320-330",
            optionD = "IPC Section 420-430",
            correctAnswer = "A",
            explanation = "IPC Sections 272 and 273 deal with sale/exposure of noxious food/drinks and causing death by negligence respectively (now supplemented by FSS Act 2006).",
            chapter = Constants.Categories.NUTRITION,
            difficulty = Constants.DIFFICULTY_HARD
        ),

        // Communicable Diseases - Additional Questions
        Question(
            questionText = "The Revised National Tuberculosis Control Programme (RNTCP) in India uses which strategy?",
            optionA = "Directly Observed Treatment Short-course (DOTS)",
            optionB = "Pulse Polio Immunization",
            optionC = "Expanded Programme on Immunization",
            optionD = "Integrated Disease Surveillance Programme",
            correctAnswer = "A",
            explanation = "RNTCP employs DOTS strategy with standardized short-course chemotherapy, observed treatment, and systematic monitoring and evaluation.",
            chapter = Constants.Categories.COMMUNICABLE,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "Which vaccine is given orally for prevention of cholera?",
            optionA = "Ty21a",
            optionB = "Dukoral",
            optionC = "Shanchol",
            optionD = "All of the above",
            correctAnswer = "B",
            explanation = "Dukoral and Shanchol are oral cholera vaccines. Ty21a is an oral typhoid vaccine. Dukoral provides protection against cholera and ETEC.",
            chapter = Constants.Categories.COMMUNICABLE,
            difficulty = Constants.DIFFICULTY_HARD
        ),

        // Maternal and Child Health - Additional Questions
        Question(
            questionText = "Janani Suraksha Yojana (JSY) provides cash assistance to:",
            optionA = "Pregnant women only",
            optionB = "Women delivering at home",
            optionC = "Women delivering in health facilities",
            optionD = "Postnatal women only",
            correctAnswer = "C",
            explanation = "JSY provides conditional cash transfer to pregnant women who opt for institutional delivery to promote safe motherhood and reduce MMR.",
            chapter = Constants.Categories.MATERNAL_CHILD,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "The Integrated Child Development Services (ICDS) scheme targets children up to:",
            optionA = "3 years",
            optionB = "5 years",
            optionC = "6 years",
            optionD = "12 years",
            correctAnswer = "C",
            explanation = "ICDS provides integrated services for children up to 6 years, including supplementary nutrition, immunization, health check-ups, and early learning.",
            chapter = Constants.Categories.MATERNAL_CHILD,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        // Health Systems - Additional Questions
        Question(
            questionText = "The National Health Mission (NHM) comprises:",
            optionA = "NRHM only",
            optionB = "NUHM only",
            optionC = "Both NRHM and NUHM",
            optionD = "Ayushman Bharat only",
            correctAnswer = "C",
            explanation = "National Health Mission includes National Rural Health Mission (NRHM) and National Urban Health Mission (NUHM) to strengthen health systems.",
            chapter = Constants.Categories.HEALTH_SYSTEMS,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "The Health Management Information System (HMIS) in India reports at:",
            optionA = "Block level only",
            optionB = "District and state levels",
            optionC = "National level only",
            optionD = "Village level only",
            correctAnswer = "B",
            explanation = "HMIS collects data from PHCs and other facilities and reports at district and state levels for planning and monitoring.",
            chapter = Constants.Categories.HEALTH_SYSTEMS,
            difficulty = Constants.DIFFICULTY_MEDIUM
        )
    )
}
