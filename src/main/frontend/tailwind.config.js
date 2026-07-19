module.exports = {
  content: ["../resources/templates/**/*.{html,js}"],
  theme: {
    extend: {
      colors: {
        // "carp common" theme — named by role, not by shade, so templates
        // never reference a raw color. Change the theme by editing only
        // the hex values below, then rebuild output.css.
        ink: "#14150D",        // page background
        surface: "#21231A",    // cards, header, footer
        "surface-alt": "#2C2E20", // nested surfaces (photo placeholders, inputs)
        // Accent color — currently Moss. To switch to Olive Drab instead,
        // comment out the two "Moss" lines below and uncomment the two
        // "Olive Drab" lines, then rebuild output.css. Only one pair should
        // be active (uncommented) at a time.

        // -- Moss (active) --
        accent: "#5C7A3D",
        "accent-hover": "#4A6330",

        // -- Olive Drab --
        // accent: "#4B5320",
        // "accent-hover": "#3A4119",

        paper: "#F5F1E6",      // primary text
        faint: "#9F9C86",      // secondary/muted text
        line: "#2E301F",       // borders/dividers
      },
    },
  },
  plugins: [],
}
