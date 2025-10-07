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

        // Add comprehensive MCQs from resources (Community_Medicine_MCQ_Bank.docx)
        questions.addAll(getTextbookMCQs())

        return questions
    }

    private fun getEpidemiologyQuestions() = listOf(
        Question(
            questionText = "Best measure of association in a cohort study is:",
            optionA = "Prevalence ratio",
            optionB = "Odds ratio",
            optionC = "Relative risk",
            optionD = "Attributable risk",
            correctAnswer = "C",
            explanation = "Cohort studies calculate incidence → allow RR estimation, best for measuring risk in longitudinal studies.",
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

    // MCQs from D:\AI Book and Content Writer\community-medicine-mcq-app\resources\Community_Medicine_MCQ_Bank.docx
    private fun getTextbookMCQs() = listOf(
        // CM1: Concept of Health and Disease
        Question(
            questionText = "Which of the following is NOT a dimension of health as per WHO?",
            optionA = "Spiritual",
            optionB = "Mental",
            optionC = "Social",
            optionD = "Genetic",
            correctAnswer = "D",
            explanation = "Genetic is a determinant of health, not a dimension. WHO includes physical, mental, social dimensions; spiritual added later.",
            chapter = Constants.Categories.HEALTH_SYSTEMS,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "The concept of 'positive health' includes:",
            optionA = "Absence of disease only",
            optionB = "Physical fitness, mental efficiency, social well-being",
            optionC = "Ability to earn livelihood only",
            optionD = "Presence of risk factors but no symptoms",
            correctAnswer = "B",
            explanation = "Positive health means functioning optimally beyond absence of disease - physical fitness + mental efficiency + social well-being.",
            chapter = Constants.Categories.HEALTH_SYSTEMS,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        // CM2: Epidemiology & Research Methods
        Question(
            questionText = "An epidemic curve with multiple peaks at interval of incubation period suggests:",
            optionA = "Common source",
            optionB = "Point source",
            optionC = "Propagated epidemic",
            optionD = "Mixed epidemic",
            correctAnswer = "C",
            explanation = "Propagated epidemics show multiple peaks as person-to-person spread continues with each generation of cases.",
            chapter = Constants.Categories.EPIDEMIOLOGY,
            difficulty = Constants.DIFFICULTY_HARD
        ),

        // CM3: Environment & Health
        Question(
            questionText = "Break-point chlorination ensures:",
            optionA = "All chlorine removed",
            optionB = "Residual chlorine after demand met",
            optionC = "Only bacteria killed",
            optionD = "Organic matter removed",
            correctAnswer = "B",
            explanation = "Break-point chlorination adds chlorine until demand is satisfied, then additional chlorine remains as free residual (0.5 mg/L).",
            chapter = Constants.Categories.ENVIRONMENTAL,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "Minimum standard floor space/person in housing is:",
            optionA = "25 sq ft",
            optionB = "50 sq ft",
            optionC = "100 sq ft",
            optionD = "150 sq ft",
            correctAnswer = "C",
            explanation = "ICMR standard for housing floor space is ≥100 sq ft per person.",
            chapter = Constants.Categories.ENVIRONMENTAL,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        // CM4: Screening for Disease
        Question(
            questionText = "A highly sensitive test will:",
            optionA = "Detect most true positives",
            optionB = "Detect most true negatives",
            optionC = "Give no false positives",
            optionD = "Miss most cases",
            correctAnswer = "A",
            explanation = "Sensitivity = proportion of true positives detected. High sensitivity means few false negatives (missed cases).",
            chapter = Constants.Categories.NON_COMMUNICABLE,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "Lead-time bias occurs in:",
            optionA = "Cohort study",
            optionB = "Screening programmes",
            optionC = "Case-control study",
            optionD = "Ecological study",
            correctAnswer = "B",
            explanation = "Lead-time bias creates apparent survival increase in screened groups due to earlier diagnosis, not real benefit.",
            chapter = Constants.Categories.NON_COMMUNICABLE,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        // CM5: Nutrition
        Question(
            questionText = "MUAC <11.5 cm in a child indicates:",
            optionA = "Normal",
            optionB = "Mild malnutrition",
            optionC = "Moderate malnutrition",
            optionD = "Severe acute malnutrition",
            correctAnswer = "D",
            explanation = "WHO cut-off for severe acute malnutrition (SAM) by MUAC is <11.5 cm.",
            chapter = Constants.Categories.NUTRITION,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "Vitamin A prophylaxis dose at 9 months is:",
            optionA = "50,000 IU",
            optionB = "1 lakh IU",
            optionC = "2 lakh IU",
            optionD = "5 lakh IU",
            correctAnswer = "B",
            explanation = "First dose of Vitamin A prophylaxis at 9 months is 1 lakh IU orally.",
            chapter = Constants.Categories.NUTRITION,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        // CM6–7: Demography & Family Planning
        Question(
            questionText = "India is in which stage of demographic cycle?",
            optionA = "Early expanding",
            optionB = "Late expanding",
            optionC = "Low stationary",
            optionD = "Declining",
            correctAnswer = "B",
            explanation = "India is in late expanding stage - high birth rates, rapidly declining death rates, population still growing.",
            chapter = Constants.Categories.DEMOGRAPHY,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "Copper-T 380A is effective for:",
            optionA = "3 years",
            optionB = "5 years",
            optionC = "10 years",
            optionD = "15 years",
            correctAnswer = "C",
            explanation = "Copper-T 380A intrauterine device provides contraception for up to 10 years.",
            chapter = Constants.Categories.DEMOGRAPHY,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        // CM8: Communicable Diseases
        Question(
            questionText = "Herd immunity is not useful in:",
            optionA = "Measles",
            optionB = "Tetanus",
            optionC = "Polio",
            optionD = "Rubella",
            correctAnswer = "B",
            explanation = "Tetanus is not transmitted person-to-person, so herd immunity doesn't apply (no community protection effect).",
            chapter = Constants.Categories.COMMUNICABLE,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "Incubation period of measles is:",
            optionA = "1–2 days",
            optionB = "5–7 days",
            optionC = "10 days",
            optionD = "3 weeks",
            correctAnswer = "C",
            explanation = "Measles incubation period is approximately 10 days (range: 7-18 days).",
            chapter = Constants.Categories.COMMUNICABLE,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        // CM9: Non-Communicable Diseases
        Question(
            questionText = "Which of the following is primordial prevention?",
            optionA = "Health education in children against smoking",
            optionB = "Vaccination",
            optionC = "Screening for HTN",
            optionD = "Rehabilitation",
            correctAnswer = "A",
            explanation = "Primordial prevention prevents emergence of risk factors (e.g., teaching children not to smoke before they start).",
            chapter = Constants.Categories.NON_COMMUNICABLE,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "Most common cancer in Indian women is:",
            optionA = "Breast",
            optionB = "Cervical",
            optionC = "Ovarian",
            optionD = "Lung",
            correctAnswer = "C",
            explanation = "Breast cancer overtook cervical as most common cancer in Indian women (ICMR data).",
            chapter = Constants.Categories.NON_COMMUNICABLE,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        // CM10: Health Statistics & Research Methods
        Question(
            questionText = "In normal distribution, 95% of values lie within:",
            optionA = "1 SD",
            optionB = "2 SD",
            optionC = "3 SD",
            optionD = "4 SD",
            correctAnswer = "B",
            explanation = "In normal distribution: 68% within 1 SD, 95.45% within 2 SD, 99.73% within 3 SD.",
            chapter = Constants.Categories.BIOSTATISTICS,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "Chi-square test is used for:",
            optionA = "Means",
            optionB = "Proportions",
            optionC = "Correlation",
            optionD = "Regression",
            correctAnswer = "B",
            explanation = "Chi-square test assesses association between categorical variables/proportions.",
            chapter = Constants.Categories.BIOSTATISTICS,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        // CM11: Occupational Health
        Question(
            questionText = "Byssinosis is caused by:",
            optionA = "Silica dust",
            optionB = "Asbestos",
            optionC = "Cotton dust",
            optionD = "Coal dust",
            correctAnswer = "C",
            explanation = "Byssinosis ('Brown Lung') is occupational lung disease in cotton mill workers caused by cotton dust.",
            chapter = Constants.Categories.HEALTH_SYSTEMS,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "Most common occupational cancer due to asbestos is:",
            optionA = "Lung cancer",
            optionB = "Bladder cancer",
            optionC = "Mesothelioma",
            optionD = "Leukemia",
            correctAnswer = "C",
            explanation = "Asbestos exposure is strongly associated with mesothelioma (pleural cancer) as most common occupational cancer.",
            chapter = Constants.Categories.HEALTH_SYSTEMS,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        // CM12: Health System in India
        Question(
            questionText = "Population covered by one Sub-Centre is:",
            optionA = "1000–2000",
            optionB = "3000–5000",
            optionC = "10,000",
            optionD = "20,000",
            correctAnswer = "B",
            explanation = "Each Sub-Centre covers 3000-5000 population in plains, 3000 in tribal/desert areas.",
            chapter = Constants.Categories.HEALTH_SYSTEMS,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "Staff at PHC includes:",
            optionA = "1 MO + 14 staff",
            optionB = "2 MO + 20 staff",
            optionC = "4 specialists",
            optionD = "ASHA workers",
            correctAnswer = "A",
            explanation = "Each Primary Health Centre has 1 Medical Officer and 14 paramedical/supporting staff.",
            chapter = Constants.Categories.HEALTH_SYSTEMS,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        // CM13: National Health Programmes
        Question(
            questionText = "Which programme includes DEC + albendazole MDA?",
            optionA = "RNTCP",
            optionB = "NVBDCP",
            optionC = "UIP",
            optionD = "NTCP",
            correctAnswer = "B",
            explanation = "National Vector Borne Disease Control Programme (NVBDCP) includes Mass Drug Administration with DEC + Albendazole for filariasis.",
            chapter = Constants.Categories.HEALTH_SYSTEMS,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "Anemia Mukt Bharat targets anemia reduction in:",
            optionA = "Children only",
            optionB = "Pregnant women only",
            optionC = "All age groups",
            optionD = "6 beneficiary groups",
            correctAnswer = "D",
            explanation = "AMB targets anemia in 6 beneficiary groups: children, adolescents, women of reproductive age, pregnant, lactating, men.",
            chapter = Constants.Categories.HEALTH_SYSTEMS,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        // CM14: International Health
        Question(
            questionText = "Alma Ata declaration year was:",
            optionA = "1975",
            optionB = "1978",
            optionC = "1981",
            optionD = "1986",
            correctAnswer = "B",
            explanation = "International Conference on Primary Health Care held in Alma Ata (Kazakhstan) in 1978.",
            chapter = Constants.Categories.HEALTH_SYSTEMS,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        Question(
            questionText = "Sustainable Development Goals (SDGs) target year is:",
            optionA = "2020",
            optionB = "2025",
            optionC = "2030",
            optionD = "2040",
            correctAnswer = "C",
            explanation = "United Nations established 17 Sustainable Development Goals (SDGs) to be achieved by 2030.",
            chapter = Constants.Categories.HEALTH_SYSTEMS,
            difficulty = Constants.DIFFICULTY_EASY
        ),

        // Additional CM1 questions for completeness
        Question(
            questionText = "Which dimension was added later to WHO's health definition?",
            optionA = "Spiritual",
            optionB = "Vocational",
            optionC = "Environmental",
            optionD = "Reproductive",
            correctAnswer = "A",
            explanation = "Spiritual dimension was added later to WHO's original definition of health (1948).",
            chapter = Constants.Categories.HEALTH_SYSTEMS,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        // Additional CM2 research methodology
        Question(
            questionText = "Which sampling method gives every person equal chance of selection?",
            optionA = "Convenience",
            optionB = "Purposive",
            optionC = "Simple random",
            optionD = "Snowball",
            correctAnswer = "C",
            explanation = "Simple random sampling ensures every element in population has equal, independent chance of selection.",
            chapter = Constants.Categories.EPIDEMIOLOGY,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        // Additional CM5 nutrition
        Question(
            questionText = "Which condition is caused by severe Vitamin B1 deficiency?",
            optionA = "Beriberi",
            optionB = "Pellagra",
            optionC = "Scurvy",
            optionD = "Rickets",
            correctAnswer = "A",
            explanation = "Beriberi is caused by thiamine (Vitamin B1) deficiency, manifesting as cardio-neurological symptoms.",
            chapter = Constants.Categories.NUTRITION,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        // Additional CM8 vaccination
        Question(
            questionText = "OPV (Oral Polio Vaccine) contains:",
            optionA = "Live attenuated vaccine",
            optionB = "Killed vaccine",
            optionC = "Subunit vaccine",
            optionD = "Toxoid vaccine",
            correctAnswer = "A",
            explanation = "OPV is live attenuated polio vaccine (Sabin vaccine) given orally, provides better mucosal immunity.",
            chapter = Constants.Categories.COMMUNICABLE,
            difficulty = Constants.DIFFICULTY_MEDIUM
        ),

        // Additional CM12 rural health
        Question(
            questionText = "First Referral Unit (FRU) serves as:",
            optionA = "Outpatient clinic only",
            optionB = "Emergency services for 4-5 PHCs",
            optionC = "District hospital",
            optionD = "Medical college",
            correctAnswer = "B",
            explanation = "FRUs provide emergency obstetric care and surgery, serving 4-5 Primary Health Centres as referral point.",
            chapter = Constants.Categories.HEALTH_SYSTEMS,
            difficulty = Constants.DIFFICULTY_MEDIUM
        )
    )
}
