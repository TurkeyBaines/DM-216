package com.dm.deadman.content.sigils;

public class Sigils {

    public class Tier_1 {

        public enum Combat {
            Rigorous_Ranger("Sigil of the Rigorous Ranger", 0),
            Meticulous_Mage("Sigil of the Meticulous Mage", 1),
            Deft_Strikes("Sigil of Deft Strikes", 2),
            Resistance("Sigil of Resistance", 3),
            Resilience("Sigil of Resilience", 4),
            Consistency("Sigil of Consistency", 5);

            public String name;
            public int playerSlotID;
            Combat(String name, int playerSlotID) { this.name = name; this.playerSlotID = playerSlotID; }
        }

        public enum Skilling {
            Agile_Fortune("Sigil of Agile Fortune", 6),
            Hoarding("Sigil of Hoarding", 7),
            Sustenance("Sigil of Sustenance", 8),
            Forager("Sigil of the Forager", 9),
            Enhanced_Harvest("Sigil of Enhanced Harvest", 10),
            Storage("Sigil of Storage", 11),
            Abyss("Sigil of the Abyss", 12),
            Deception("Sigil of Deception", 13),
            Litheness("Sigil of Litheness", 14);

            public String name;
            public int playerSlotID;
            Skilling(String name, int playerSlotID) { this.name = name; this.playerSlotID = playerSlotID; }
        }

        public enum Utility {
            Alchemaniac("Sigil of the Alchemaniac", 15),
            Food_Master("Sigil of the Food Master", 16),
            Well_Fed("Sigil of the Well Fed", 17),
            Potion_Master("Sigil of the Potion Master", 18),
            Eternal_Jeweller("Sigil of the Eternal_Jeweller", 19),
            Treasure_Hunter("Sigil of the Treasure Hunter", 20),
            Mobility("Sigil of Mobility", 21),
            Exaggeration("Sigil of Exaggeration", 22);

            public String name;
            public int playerSlotID;
            Utility(String name, int playerSlotID) { this.name = name; this.playerSlotID = playerSlotID; }
        }

    }

    public class Tier_2 {
        public enum Combat {
            Menacing_Mage("Sigil of the Menacing Mage", 23),
            Formidable_Fighter("Sigil of the Formidable Fighter", 24),
            Ruthless_Ranger("Sigil of the Ruthless Ranger", 25),
            Augmented_Thrall("Sigil of the Augmented Thrall", 26),
            Lightbearer("Sigil of the Lightbearer", 27),
            Precision("Sigil of Precision", 28),
            Specialised_Strikes("Sigil of Specialised Strikes", 29),
            Porcupine("Sigil of Porcupine", 30),
            Binding("Sigil of Binding", 31),
            Fortification("Sigil of Fortification", 32),
            Swashbuckler("Sigil of the Swashbuckler", 33),
            Gunslinger("Sigil of the Gunslinger", 34),
            Arcane_Swiftness("Sigil of Arcane Swiftness", 35),
            Adroit("Sigil of the Adroit", 36),
            Restoration("Sigil of Restoration", 37),
            Barrows("Sigil of Barrows", 38);

            public String name;
            public int playerSlotID;
            Combat(String name, int playerSlotID) { this.name = name; this.playerSlotID = playerSlotID; }
        }

        public enum Skilling {
            Hunter("Sigil of the Hunter", 39),
            Infernal_Chef("Sigil of the Infernal Chef", 40),
            Infernal_Smith("Sigil of the Infernal Smith", 41),
            Nature("Sigil of Nature", 42),
            Devotion("Sigil of Devotion", 43);

            public String name;
            public int playerSlotID;
            Skilling(String name, int playerSlotID) { this.name = name; this.playerSlotID = playerSlotID; }
        }

        public enum Utility {
            Revoked_Limitation("Sigil of Revoked Limitation", 44),
            Last_Recall("Sigil of Last Recall", 45),
            Bloodhound("Sigil of the Bloodhound", 46),
            Faith("Sigil of Faith", 47),
            Prosperity("Sigil of Prosperity", 48),
            Slaughter("Sigil of Slaughter", 49),
            Fortune_Farmer("Sigil of the Fortune Farmer", 50),
            Versatility("Sigil of Versatility", 51),
            Serpent("Sigil of the Serpent", 52),
            Preservation("Sigil of Preservation", 53);

            public String name;
            public int playerSlotID;
            Utility(String name, int playerSlotID) { this.name = name; this.playerSlotID = playerSlotID; }
        }
    }

    public class Tier_3 {
        public enum Combat {
            Titanium("Sigil of Titanium", 54),
            Finality("Sigil of Finality", 55),
            Pious_Protection("Sigil of Pious Protection", 56),
            Aggression("Sigil of Aggression", 57),
            Rampage("Sigil of Rampage", 58),
            Meticulousness("Sigil of Meticulousness", 59),
            Rampart("Sigil of the Rampart", 60);

            public String name;
            public int playerSlotID;
            Combat(String name, int playerSlotID) { this.name = name; this.playerSlotID = playerSlotID; }
        }

        public enum Skilling {
            Woodcraft("Sigil of Woodcraft", 61),
            Remote_Storage("Sigil of Remote Storage", 62);

            public String name;
            public int playerSlotID;
            Skilling(String name, int playerSlotID) { this.name = name; this.playerSlotID = playerSlotID; }
        }

        public enum Utility {
            Guardian_Angel("Sigil of the Guardian Angel", 63);

            public String name;
            public int playerSlotID;
            Utility(String name, int playerSlotID) { this.name = name; this.playerSlotID = playerSlotID; }
        }
    }

    public enum Togglable {
        Devotion("Sigil of Devotion", 64),
        Enhanced_Harvest("Sigil of the Enhanced Harvest", 65),
        Remote_Storage("Sigil of Remote Storage", 66),
        Slaughter("Sigil of Slaughter", 67);

        String name;
        public int playerSlotID;
        Togglable(String name, int playerSlotID) { this.name = name; this.playerSlotID = playerSlotID; }
    }
}
