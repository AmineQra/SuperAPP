module.exports = {
  content: [
    "./src/**/*.{html,ts}", // Tailwind needs to scan Angular templates
  ],
  theme: {
    extend: {
      animation: {
        "fade-in-down": "fade-in-down 0.3s ease-out",
        "fade-in-up": "fade-in-up 0.3s ease-out",
        "slide-down": "slide-down 0.3s ease-out",
        "pulse-once": "pulse 1s cubic-bezier(0.4, 0, 0.6, 1)",
        "scale-in": "scale-in 0.2s ease-out",
        "fade-in": "fade-in 0.2s ease-out",
      },
      keyframes: {
        "fade-in-down": {
          "0%": { opacity: "0", transform: "translateY(-10px)" },
          "100%": { opacity: "1", transform: "translateY(0)" },
        },
        "fade-in-up": {
          "0%": { opacity: "0", transform: "translateY(10px)" },
          "100%": { opacity: "1", transform: "translateY(0)" },
        },
        "slide-down": {
          "0%": { opacity: "0", maxHeight: "0" },
          "100%": { opacity: "1", maxHeight: "500px" },
        },
        "scale-in": {
          "0%": { transform: "scale(0.95)", opacity: "0" },
          "100%": { transform: "scale(1)", opacity: "1" },
        },
        "fade-in": {
          "0%": { opacity: "0" },
          "100%": { opacity: "1" },
        },
      },
    },
  },
  plugins: [],
};
